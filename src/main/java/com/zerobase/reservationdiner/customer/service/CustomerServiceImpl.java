package com.zerobase.reservationdiner.customer.service;

import com.zerobase.reservationdiner.customer.dto.StoreInfo;

import com.zerobase.reservationdiner.customer.repository.StoreRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class CustomerServiceImpl implements CustomerService{

    private final StoreRepository storeRepository;
    @Override
    public List<StoreInfo> getAllStore(double latitude, double longtitude) {

        return storeRepository.findBydistanceOrderByStoreNameAndStart(latitude,longtitude);
    }
}
