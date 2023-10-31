package com.zerobase.reservationdiner.customer.service;

import com.zerobase.reservationdiner.customer.dto.StoreInfo;

import java.util.List;

public interface CustomerService {
    List<StoreInfo> getAllStore(double latitude, double longtitude);
}
