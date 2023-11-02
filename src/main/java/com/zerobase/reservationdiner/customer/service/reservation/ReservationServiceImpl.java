package com.zerobase.reservationdiner.customer.service.reservation;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.google.firebase.messaging.FirebaseMessagingException;
import com.zerobase.reservationdiner.customer.domain.Reservation;
import com.zerobase.reservationdiner.customer.dto.reservation.ReservationInfo;
import com.zerobase.reservationdiner.customer.exception.reservation.ReservationException;
import com.zerobase.reservationdiner.customer.exception.store.StoreException;
import com.zerobase.reservationdiner.customer.repository.reservation.ReservationRepository;
import com.zerobase.reservationdiner.customer.type.ReservationErrorCode;
import com.zerobase.reservationdiner.customer.type.StoreErrorCode;
import com.zerobase.reservationdiner.fcmn.dto.FcmMessage;
import com.zerobase.reservationdiner.fcmn.service.FcmService;
import com.zerobase.reservationdiner.owner.domain.TimeSlot;
import com.zerobase.reservationdiner.owner.repository.TimeSlotRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.util.List;

@Service
@RequiredArgsConstructor
public class ReservationServiceImpl implements ReservationService{

    private final ReservationRepository reservationRepository;
    private final FcmService fcmService;
    private final StringRedisTemplate redisTemplate;
    private final TimeSlotRepository timeSlotRepository;

    @Override
    public ReservationInfo.Response reservationStore(ReservationInfo.Request request) {
        TimeSlot reserveTime = timeSlotRepository.findById(request.getTimeslotId()).orElseThrow(() -> new ReservationException(ReservationErrorCode.INVALID_TIME));
        if(reserveTime.getReserve()){
            throw new StoreException(StoreErrorCode.ALREADY_RESERVE_TABLE);
        }

        Reservation reservation = ReservationInfo.Request.of(request);
        Reservation reserveSuccess = reservationRepository.save(reservation);
        reserveTime.setReserve(true);
        timeSlotRepository.save(reserveTime);

        if(redisTemplate.opsForValue().get(reserveSuccess.getStoreId())==null){
            try {
                String accessToken = fcmService.getAccessToken(reserveSuccess.getStoreId());
                sendMessageToOwner(reserveSuccess, accessToken);

            } catch (IOException | FirebaseMessagingException e) {
                throw new RuntimeException(e);
            }
        }else{
            String accessToken = redisTemplate.opsForValue().get(reserveSuccess.getStoreId());
            try {
                sendMessageToOwner(reserveSuccess, accessToken);

            } catch (IOException | FirebaseMessagingException e) {
                throw new RuntimeException(e);
            }
        }
        return ReservationInfo.Response.of(reserveSuccess);
    }

    private void sendMessageToOwner(Reservation reserveSuccess, String accessToken) throws JsonProcessingException, FirebaseMessagingException {
        FcmMessage.Request message = FcmMessage.Request.builder()
                .reserveTime(reserveSuccess.getReservationDate())
                .userName(reserveSuccess.getMemberId())
                .targetToken(accessToken)
                .build();
        fcmService.sendMessage(message);
    }
}
