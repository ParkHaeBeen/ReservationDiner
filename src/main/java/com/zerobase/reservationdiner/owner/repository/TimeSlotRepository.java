package com.zerobase.reservationdiner.owner.repository;

import com.zerobase.reservationdiner.owner.domain.TimeSlot;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface TimeSlotRepository extends JpaRepository<TimeSlot,Long> {
    List<TimeSlot> findByStoreId(Long storeId);
}
