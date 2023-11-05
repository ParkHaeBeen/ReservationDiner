package com.zerobase.reservationdiner.owner.repository.timeslot;

import com.zerobase.reservationdiner.kiosk.dto.ReservationCheck;

import java.time.LocalDateTime;

public interface CustomTimeSlotRepository {
    ReservationCheck findByMemberIdAndOwnerStoreId(String memberId, Long ownerStoreId, LocalDateTime arriveTime);
}
