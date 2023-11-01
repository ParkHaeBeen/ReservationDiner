package com.zerobase.reservationdiner.owner.repository;

import com.zerobase.reservationdiner.owner.domain.OwnerStore;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface OwnerRepository extends JpaRepository<OwnerStore,Long> ,CustomOwnerRepository {
    boolean existsByStoreNameAndAddressZipcode(String storeName,String zipCode);

    Optional<OwnerStore> findByStoreName(String storeName);
}
