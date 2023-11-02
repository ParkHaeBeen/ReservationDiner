package com.zerobase.reservationdiner.customer.service.store;

import com.zerobase.reservationdiner.customer.dto.store.StoreDetailInfo;
import com.zerobase.reservationdiner.customer.dto.store.StoreInfo;

import com.zerobase.reservationdiner.customer.exception.store.StoreException;
import com.zerobase.reservationdiner.customer.type.StoreErrorCode;
import com.zerobase.reservationdiner.owner.domain.OwnerStore;
import com.zerobase.reservationdiner.owner.repository.OwnerRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class StoreServiceImpl implements StoreService {

    private final OwnerRepository ownerRepository;
    @Override
    public List<StoreInfo> getAllStore(double latitude, double longtitude,String storeName) {
        if(storeName==null||storeName.equals("")){
            return ownerRepository.findBydistanceOrderByStoreNameAndStar(latitude,longtitude);
        }

        List<StoreInfo> storeByName = ownerRepository.findBydistanceAndStoreNameOrderByStoreNameAndStar(latitude, longtitude, storeName);
        if(storeByName.size()==0){
            throw new StoreException(StoreErrorCode.NOTFOUND_STORE);
        }

        return storeByName;
    }

    @Override
    public StoreDetailInfo getStore(Long storeId) {
        OwnerStore store = ownerRepository.findById(storeId).orElseThrow(() -> new StoreException(StoreErrorCode.NOTFOUND_STORE));

        return StoreDetailInfo.of(store);
    }
}
