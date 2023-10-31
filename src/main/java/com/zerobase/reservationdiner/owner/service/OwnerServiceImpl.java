package com.zerobase.reservationdiner.owner.service;

import com.zerobase.reservationdiner.owner.domain.OwnerStore;
import com.zerobase.reservationdiner.owner.dto.StoreInput;
import com.zerobase.reservationdiner.owner.exception.OwnerException;
import com.zerobase.reservationdiner.owner.repository.OwnerRepository;
import com.zerobase.reservationdiner.owner.type.OwnerErrorCode;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

@Service
@Slf4j
@RequiredArgsConstructor
public class OwnerServiceImpl implements OwnerService{

    private final OwnerRepository ownerRepository;

    @Override
    public void registerStore(StoreInput input) {
        boolean exist = ownerRepository.existsByStoreNameAndZipcode(input.getStoreName(), input.getZipcode());

        if(exist) {
            throw new OwnerException(OwnerErrorCode.ALREADY_EXIST);
        }
        OwnerStore newStore = StoreInput.of(input);
        ownerRepository.save(newStore);

    }

}
