package com.zerobase.reservationdiner.customer.service.store;

import com.zerobase.reservationdiner.customer.dto.store.StoreDetailInfo;
import com.zerobase.reservationdiner.customer.dto.store.StoreInfo;

import com.zerobase.reservationdiner.customer.exception.store.StoreException;
import com.zerobase.reservationdiner.customer.type.StoreErrorCode;
import com.zerobase.reservationdiner.owner.domain.OwnerStore;
import com.zerobase.reservationdiner.owner.domain.TimeSlot;
import com.zerobase.reservationdiner.owner.repository.owner.OwnerRepository;
import com.zerobase.reservationdiner.owner.repository.timeslot.TimeSlotRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
@Slf4j
public class StoreServiceImpl implements StoreService {

    private final OwnerRepository ownerRepository;
    private final TimeSlotRepository  timeSlotRepository;

    @Override
    public List<StoreInfo> getAllStore(double latitude, double longtitude,String storeName) {
        List<StoreInfo> storeByName;
        if(storeName==null||storeName.equals("")){
            storeByName=ownerRepository.findBydistanceOrderByStoreNameAndStar(latitude,longtitude);
        }else{
            storeByName = ownerRepository.findBydistanceAndStoreNameOrderByStoreNameAndStar(latitude, longtitude, storeName);
        }

        if(storeByName.size()==0){
            throw new StoreException(StoreErrorCode.NOTFOUND_STORE);
        }

        return storeByName;
    }

    @Override
    public StoreDetailInfo getStore(Long storeId) {
        OwnerStore store = ownerRepository.findById(storeId).orElseThrow(() -> new StoreException(StoreErrorCode.NOTFOUND_STORE));
        List<TimeSlot> possibleTimeSlot = timeSlotRepository.findByStoreIdAndReserveFalse(storeId);
        store.setTimeSlots(possibleTimeSlot);
        return StoreDetailInfo.of(store);
    }
}
