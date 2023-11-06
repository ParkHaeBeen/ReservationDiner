package com.zerobase.reservationdiner.customer.service.reservation;

import com.zerobase.reservationdiner.customer.domain.Reservation;
import com.zerobase.reservationdiner.customer.dto.reservation.ReservationInfo;
import com.zerobase.reservationdiner.customer.repository.reservation.ReservationRepository;
import com.zerobase.reservationdiner.member.domain.Member;
import com.zerobase.reservationdiner.member.type.MemberGrade;
import com.zerobase.reservationdiner.owner.domain.Address;
import com.zerobase.reservationdiner.owner.domain.OwnerStore;
import com.zerobase.reservationdiner.owner.domain.TimeSlot;
import com.zerobase.reservationdiner.owner.repository.timeslot.TimeSlotRepository;
import jakarta.persistence.EntityManager;
import jakarta.transaction.Transactional;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.annotation.Rollback;

import java.time.LocalDateTime;
import java.time.LocalTime;
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

    @Autowired
    private TimeSlotRepository timeSlotRepository;

    @Autowired
    private EntityManager em;

    //FCM가 에러가 나 통과하지 않음
    @Test
    void reservationStoreSuccesstest(){
        //given

        Member member=Member.builder()
                .memberName("test")
                .memberId("test123")
                .roles(List.of(MemberGrade.ROLE_CUSTOMER.toString()))
                .memberPassword("12345")
                .phoneNumber("010-1234-1234")
                .build();

        em.persist(member);

        OwnerStore store=OwnerStore.builder()
                .storeDescription("맛잇다 마잇다 마잇다")
                .storeName("맛나다")
                .openStatus(true)
                .openTime(LocalTime.of(12,0))
                .closeTime(LocalTime.of(23,0))
                .address(Address.builder().build())
                .owner(member)
                .build();

        em.persist(store);
        em.flush();
        TimeSlot timeSlot=TimeSlot.builder()
                .time(LocalDateTime.now())
                .store(store)
                .reserve(false)
                .reservation(Reservation.builder()
                        .phoneNumber("010-1234-1234")
                        .customerCnt(2)
                        .member(member)
                        .build())
                .build();

        timeSlotRepository.save(timeSlot);

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