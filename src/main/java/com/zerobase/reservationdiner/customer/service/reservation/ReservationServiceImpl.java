package com.zerobase.reservationdiner.customer.service.reservation;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.google.firebase.messaging.FirebaseMessagingException;
import com.zerobase.reservationdiner.customer.domain.Reservation;
import com.zerobase.reservationdiner.customer.dto.reservation.ReservationInfo;
import com.zerobase.reservationdiner.customer.exception.reservation.ReservationException;
import com.zerobase.reservationdiner.customer.repository.reservation.ReservationRepository;
import com.zerobase.reservationdiner.customer.type.ReservationErrorCode;
import com.zerobase.reservationdiner.fcmn.dto.FcmMessage;
import com.zerobase.reservationdiner.fcmn.service.FcmService;
import com.zerobase.reservationdiner.member.domain.Member;
import com.zerobase.reservationdiner.member.repository.MemberRepository;
import com.zerobase.reservationdiner.owner.domain.TimeSlot;
import com.zerobase.reservationdiner.owner.repository.timeslot.TimeSlotRepository;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.stereotype.Service;

import java.io.IOException;

@Service
@RequiredArgsConstructor
@Transactional
public class ReservationServiceImpl implements ReservationService{

    private final ReservationRepository reservationRepository;
    private final MemberRepository memberRepository;
    private final FcmService fcmService;
    private final StringRedisTemplate redisTemplate;
    private final TimeSlotRepository timeSlotRepository;

    @Override
    public ReservationInfo.Response reservationStore(ReservationInfo.Request request) {
        Reservation reserveSuccess = validationReservationInfoAndSave(request);
        sendAlarmToOwner(reserveSuccess);
        return ReservationInfo.Response.of(reserveSuccess);
    }



    private Reservation validationReservationInfoAndSave(ReservationInfo.Request request){
        Member member = memberRepository.findByMemberId(request.getMemberId()).orElseThrow(() -> new ReservationException(ReservationErrorCode.INVALID_LOGIN));
        TimeSlot reserveTime = timeSlotRepository.findById(request.getTimeslotId()).orElseThrow(() -> new ReservationException(ReservationErrorCode.INVALID_TIME));
        if(reserveTime.getReserve()){
            throw new ReservationException(ReservationErrorCode.ALREADY_RESERVE_TABLE);
        }

        Reservation reservation = ReservationInfo.Request.of(request);
        reservation.setTimeSlot(reserveTime);
        reservation.setMember(member);

        Reservation reserveSuccess = reservationRepository.save(reservation);

        reserveTime.setReserve(true);
        timeSlotRepository.save(reserveTime);

        return  reserveSuccess;
    }
    private void sendAlarmToOwner(Reservation reserveSuccess) {
        if(redisTemplate.opsForValue().get(reserveSuccess.getTimeSlot().getStore().getId())==null){
            try {
                String accessToken = fcmService.getAccessToken(reserveSuccess.getTimeSlot().getStore().getId());
                sendMessageToOwner(reserveSuccess, accessToken);

            } catch (IOException | FirebaseMessagingException e) {
                throw new RuntimeException(e);
            }
        }else{
            String accessToken = redisTemplate.opsForValue().get(reserveSuccess.getTimeSlot().getStore().getId());
            try {
                sendMessageToOwner(reserveSuccess, accessToken);

            } catch (IOException | FirebaseMessagingException e) {
                throw new RuntimeException(e);
            }
        }
    }

    private void sendMessageToOwner(Reservation reserveSuccess, String accessToken) throws JsonProcessingException, FirebaseMessagingException {
        FcmMessage.Request message = FcmMessage.Request.builder()
                .reserveTime(reserveSuccess.getTimeSlot().getTime())
                .userName(reserveSuccess.getMember().getMemberName())
                .targetToken(accessToken)
                .build();
        fcmService.sendMessage(message);
    }
}
