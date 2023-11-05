package com.zerobase.reservationdiner.owner.repository.owner;

import com.zerobase.reservationdiner.customer.dto.store.StoreInfo;

import java.util.List;

public interface CustomOwnerRepository {
    List<StoreInfo> findBydistanceOrderByStoreNameAndStar(double latitude, double longtitude);

    List<StoreInfo> findBydistanceAndStoreNameOrderByStoreNameAndStar(double latitude, double longtitude,
                                                                      String storeName);


}
