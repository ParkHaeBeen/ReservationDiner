package com.zerobase.reservationdiner.owner.repository.owner;

import com.zerobase.reservationdiner.owner.domain.OwnerStore;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface OwnerRepository extends JpaRepository<OwnerStore,Long> ,CustomOwnerRepository {

    boolean existsByStoreNameAndAddressZipcode(String storeName,String zipCode);

    Optional<OwnerStore> findByStoreName(String storeName);

    Optional<OwnerStore> findByIdAndOwnerId(Long id, Long ownerId);

    Optional<OwnerStore> findByOwnerIdAndStoreName(Long ownerId,String storeName);

}
