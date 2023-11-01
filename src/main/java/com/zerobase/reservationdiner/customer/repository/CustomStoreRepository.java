package com.zerobase.reservationdiner.customer.repository;

import com.zerobase.reservationdiner.customer.dto.StoreInfo;

import java.util.List;

public interface CustomStoreRepository {
    List<StoreInfo> findBydistanceOrderByStoreNameAndStart(double latitude, double longtitude);
}
