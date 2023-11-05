package com.zerobase.reservationdiner.kiosk.service;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.google.firebase.messaging.FirebaseMessagingException;
import com.zerobase.reservationdiner.customer.domain.Reservation;
import com.zerobase.reservationdiner.fcmn.dto.FcmMessage;
import com.zerobase.reservationdiner.fcmn.service.FcmService;
import com.zerobase.reservationdiner.kiosk.dto.ArriveCheck;
import com.zerobase.reservationdiner.kiosk.dto.ArriveInfo;
import com.zerobase.reservationdiner.kiosk.exception.KioskException;
import com.zerobase.reservationdiner.kiosk.type.KioskErrorCode;
import com.zerobase.reservationdiner.member.domain.Member;
import com.zerobase.reservationdiner.member.repository.MemberRepository;
import com.zerobase.reservationdiner.owner.domain.OwnerStore;
import com.zerobase.reservationdiner.kiosk.dto.ReservationCheck;
import com.zerobase.reservationdiner.owner.domain.TimeSlot;
import com.zerobase.reservationdiner.owner.repository.owner.OwnerRepository;
import com.zerobase.reservationdiner.owner.repository.timeslot.TimeSlotRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.time.LocalDateTime;

@Service
@RequiredArgsConstructor
public class KioskServiceImpl implements KioskService{

    private final TimeSlotRepository timeSlotRepository;
    private final MemberRepository memberRepository;
    private final OwnerRepository ownerRepository;
    private final FcmService fcmService;
    private final StringRedisTemplate redisTemplate;


    @Override
    public ArriveInfo.Response getReserveInfo(ArriveInfo.Request info) {
        Member arriveMember = memberRepository.findByMemberId(info.getMemberId()).orElseThrow(() -> new KioskException(KioskErrorCode.NOT_FOUND_MEMBER));
        OwnerStore store = ownerRepository.findById(info.getOwnerStoreId()).orElseThrow(() -> new KioskException(KioskErrorCode.INVALID_STORE));

        ReservationCheck reserveCheck = timeSlotRepository.findByMemberIdAndOwnerStoreId(arriveMember.getMemberId(), store.getId(), info.getArriveTime());

        validationReserveStatus(info, reserveCheck);

        return ArriveInfo.Response.builder()
                .customerCnt(reserveCheck.getCustomerCnt())
                .timeSlotTime(reserveCheck.getTime())
                .memberName(arriveMember.getMemberName())
                .timeSlotId(reserveCheck.getTimeSlotId())
                .build();
    }

    private static void validationReserveStatus(ArriveInfo.Request info, ReservationCheck reserveCheck) {
        if(reserveCheck.getVisit()){
            throw new KioskException(KioskErrorCode.ARLEADY_CHECK);
        }
        if(reserveCheck.getTime().plusMinutes(10).isAfter(info.getArriveTime())){
            throw new KioskException(KioskErrorCode.LATE_FOR_CHECK);
        }
    }

    @Override
    public void getCheckVisit(ArriveCheck check) {
        TimeSlot timeSlot = timeSlotRepository.findById(check.getTimeSlotId()).orElseThrow(() -> new KioskException(KioskErrorCode.NOT_EXIST_TIMESLOT));

        if(check.getCheck()){
            timeSlot.getReservation().setVisit(true);
            sendAlaramToOwner(timeSlot.getStore().getId(),timeSlot.getReservation().getMember().getMemberName(),timeSlot.getTime());
        }
    }

    private void sendAlaramToOwner(Long storeId, String customerName, LocalDateTime timeSlot) {
        if(redisTemplate.opsForValue().get(storeId)==null){
            try {
                String accessToken = fcmService.getAccessToken(storeId);
                sendMessageToOwner(customerName,accessToken,timeSlot);

            } catch (IOException | FirebaseMessagingException e) {
                throw new RuntimeException(e);
            }
        }else{
            String accessToken = redisTemplate.opsForValue().get(storeId);
            try {
                sendMessageToOwner(customerName,accessToken,timeSlot);

            } catch (IOException | FirebaseMessagingException e) {
                throw new RuntimeException(e);
            }
        }
    }

    private void sendMessageToOwner(String customerName,String accessToken,LocalDateTime time) throws JsonProcessingException, FirebaseMessagingException {
        FcmMessage.Request message = FcmMessage.Request.builder()
                .reserveTime(time)
                .userName(customerName)
                .targetToken(accessToken)
                .build();
        fcmService.sendMessage(message);
    }
}
