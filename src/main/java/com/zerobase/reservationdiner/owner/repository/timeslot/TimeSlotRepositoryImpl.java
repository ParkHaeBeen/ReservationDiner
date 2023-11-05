package com.zerobase.reservationdiner.owner.repository.timeslot;

import com.querydsl.core.types.Projections;
import com.querydsl.jpa.impl.JPAQueryFactory;
import com.zerobase.reservationdiner.kiosk.dto.ReservationCheck;
import lombok.RequiredArgsConstructor;

import java.time.LocalDate;
import java.time.LocalDateTime;

import static com.zerobase.reservationdiner.customer.domain.QReservation.*;
import static com.zerobase.reservationdiner.owner.domain.QTimeSlot.*;

@RequiredArgsConstructor
public class TimeSlotRepositoryImpl implements CustomTimeSlotRepository{
    private final JPAQueryFactory queryFactory;

    @Override
    public ReservationCheck findByMemberIdAndOwnerStoreId(String memberId, Long ownerStoreId, LocalDateTime arriveTime){
        return queryFactory.select(Projections.constructor(ReservationCheck.class,
                        timeSlot.time,reservation.customerCnt,timeSlot.id,reservation.visit))
                .from(timeSlot)
                .innerJoin(reservation, timeSlot.reservation)
                .on(timeSlot.id.eq(reservation.timeSlot.id))
                .where(reservation.member.memberId.eq(memberId)
                        .and(timeSlot.store.id.eq(ownerStoreId))
                        .and(timeSlot.time.between(arriveTime, LocalDateTime.from(LocalDate.now()).plusDays(1)))
                        .and(timeSlot.reserve.isTrue())
                        .and(reservation.cancel.isFalse())
                        .and(reservation.ownercheck.isTrue()))
                .limit(1)
                .fetchOne();

    }
}
