package com.zerobase.reservationdiner.customer.service;

import com.querydsl.jpa.impl.JPAQueryFactory;
import com.zerobase.reservationdiner.customer.dto.StoreInfo;
import com.zerobase.reservationdiner.customer.repository.CustomStoreRepository;
import com.zerobase.reservationdiner.customer.repository.StoreRepository;
import com.zerobase.reservationdiner.owner.domain.Address;
import com.zerobase.reservationdiner.owner.domain.OwnerStore;
import jakarta.transaction.Transactional;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.context.annotation.Bean;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
@Transactional
@Slf4j
class CustomerServiceImplTest {

    @Autowired
    private CustomerService customerService;

    @Autowired
    private StoreRepository storeRepository;


    @Test
    void findAllSuccessTest(){
        //given
        OwnerStore store=OwnerStore.builder()
                .address(new Address("서울시","첨당동","1234",12.0,15.0))
                .storeDescription("맛있다 맛있어 분식. 떡뽁이, 돈까스")
                .storeName("맛나 분식")
                .ownerId("test123")
                .star(2.5)
                .build();

        OwnerStore store2=OwnerStore.builder()
                .address(new Address("서울시","첨당동","1234",20.0,18.0))
                .storeDescription("맛있다 맛있어 분식. 떡뽁이, 돈까스")
                .storeName("요리 품격")
                .ownerId("test123")
                .star(2.5)
                .build();
        storeRepository.save(store);
        storeRepository.save(store2);

        //when
        List<StoreInfo> allStore = customerService.getAllStore(12.0, 15.0);

        //then
        Assertions.assertNotNull(allStore);
        for (StoreInfo sto : allStore) {
            log.info("Store Name: " + sto.getStoreName());
            log.info("Address: " + sto.getAddress());
            log.info("ID: " + sto.getStoreId());
            log.info("Star: " + sto.getStar());
        }
    }


}