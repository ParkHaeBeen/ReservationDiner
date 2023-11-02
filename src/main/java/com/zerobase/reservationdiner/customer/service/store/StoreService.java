package com.zerobase.reservationdiner.customer.service.store;

import com.zerobase.reservationdiner.customer.dto.store.StoreDetailInfo;
import com.zerobase.reservationdiner.customer.dto.store.StoreInfo;

import java.util.List;

public interface StoreService {
    List<StoreInfo> getAllStore(double latitude, double longtitude,String storeName);

    StoreDetailInfo getStore(Long storeId);
}
