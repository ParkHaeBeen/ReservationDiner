package com.zerobase.reservationdiner.owner.repository.timeslot;

import com.zerobase.reservationdiner.owner.domain.TimeSlot;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.time.LocalDateTime;
import java.util.List;

@Repository
public interface TimeSlotRepository extends JpaRepository<TimeSlot,Long> ,CustomTimeSlotRepository{
    List<TimeSlot> findByStoreId(Long storeId);

    List<TimeSlot> findByStoreIdAndReserveTrueAndTimeBetween(Long storeId, LocalDateTime startDate,LocalDateTime endDate);
    List<TimeSlot> findByStoreIdAndReserveFalse(Long storeId);
}
