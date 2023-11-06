package com.zerobase.reservationdiner.customer.repository.review;

import com.zerobase.reservationdiner.customer.domain.Reservation;
import com.zerobase.reservationdiner.customer.domain.Review;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface ReviewRepository extends JpaRepository<Review,Long> {
    Optional<Review> findByMemberIdAndReservationId(Long memberId, Long reservationId);
}
