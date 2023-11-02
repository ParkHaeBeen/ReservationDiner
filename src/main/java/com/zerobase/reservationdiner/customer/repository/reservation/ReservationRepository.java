package com.zerobase.reservationdiner.customer.repository.reservation;

import com.zerobase.reservationdiner.customer.domain.Reservation;
import com.zerobase.reservationdiner.customer.dto.reservation.ReservationInfo;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.time.LocalDateTime;
import java.util.List;

@Repository
public interface ReservationRepository extends JpaRepository<Reservation,Long> {
    List<Reservation> findByStoreIdAndReservationDate(Long storeId, LocalDateTime time);
}
