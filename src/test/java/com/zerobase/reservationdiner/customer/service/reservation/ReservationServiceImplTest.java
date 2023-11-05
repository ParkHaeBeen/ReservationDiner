package com.zerobase.reservationdiner.customer.service.reservation;

import com.zerobase.reservationdiner.customer.domain.Reservation;
import com.zerobase.reservationdiner.customer.dto.reservation.ReservationInfo;
import com.zerobase.reservationdiner.customer.repository.reservation.ReservationRepository;
import com.zerobase.reservationdiner.owner.domain.TimeSlot;
import jakarta.transaction.Transactional;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.annotation.Rollback;

import java.time.LocalDateTime;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
@Transactional
@Slf4j
class ReservationServiceImplTest {

    @Autowired
    private ReservationRepository reservationRepository;

    @Autowired
    private ReservationService reservationService;

    @Test
    @Rollback(value = false)
    void reservationStoreSuccesstest(){
        //given
        TimeSlot timeSlot=TimeSlot.builder()
                .time(LocalDateTime.now())
                .store(null)
                .reserve(null)
                .build();

        ReservationInfo.Request request=ReservationInfo.Request.builder()
                .reservationDate(LocalDateTime.now())
                .customerCnt(2)
                .timeslotId(timeSlot.getId())
                .memberId("test123")
                .phoneNumber("010-1234-1234")
                .build();

        //when
        ReservationInfo.Response response = reservationService.reservationStore(request);
        List<Reservation> allReservation = reservationRepository.findAll();

        //then
        for (Reservation reservation : allReservation) {
            Assertions.assertEquals(response.getCustomerCnt(),reservation.getCustomerCnt());
            Assertions.assertEquals(response.getReservationDate(),reservation.getTimeSlot().getTime());
        }
    }

}