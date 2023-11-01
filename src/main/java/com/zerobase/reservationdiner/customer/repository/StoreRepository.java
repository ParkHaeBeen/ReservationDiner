package com.zerobase.reservationdiner.customer.repository;

import com.zerobase.reservationdiner.customer.domain.Reservation;
import com.zerobase.reservationdiner.owner.domain.OwnerStore;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface StoreRepository extends JpaRepository<OwnerStore,Long>,CustomStoreRepository {

}
