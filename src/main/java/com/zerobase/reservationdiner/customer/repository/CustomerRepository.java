package com.zerobase.reservationdiner.customer.repository;

import com.zerobase.reservationdiner.customer.domain.Reservation;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface CustomerRepository extends JpaRepository<Reservation,Long> {
}
