package com.zerobase.reservationdiner.customer.service.store;

import com.zerobase.reservationdiner.customer.dto.store.StoreDetailInfo;
import com.zerobase.reservationdiner.customer.dto.store.StoreInfo;
import com.zerobase.reservationdiner.customer.exception.store.StoreException;
import com.zerobase.reservationdiner.customer.service.store.StoreService;
import com.zerobase.reservationdiner.owner.domain.Address;
import com.zerobase.reservationdiner.owner.domain.OwnerStore;
import com.zerobase.reservationdiner.owner.repository.OwnerRepository;
import jakarta.transaction.Transactional;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.time.LocalTime;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
@Transactional
@Slf4j
class StoreServiceImplTest {

    @Autowired
    private StoreService storeService;

    @Autowired
    private OwnerRepository storeRepository;


    @Test
    void findAllSuccessTest(){
        //given
        insertStore();

        //when
        List<StoreInfo> allStore = storeService.getAllStore(12.0, 15.0,"");

        //then
        Assertions.assertNotNull(allStore);
        for (StoreInfo sto : allStore) {
            log.info("Store Name: " + sto.getStoreName());
            log.info("Address: " + sto.getAddress());
            log.info("ID: " + sto.getStoreId());
            log.info("Star: " + sto.getStar());
        }
    }

    @Test
    void findByStoreNameSuccessTest(){
        //given
        insertStore();

        //when
        List<StoreInfo> allStore = storeService.getAllStore(12.0, 15.0,"요리");

        //then
        Assertions.assertNotNull(allStore);
        for (StoreInfo sto : allStore) {
            log.info("Store Name: " + sto.getStoreName());
            log.info("Address: " + sto.getAddress());
            log.info("ID: " + sto.getStoreId());
            log.info("Star: " + sto.getStar());
        }
    }

    @Test
    void findByStoreNameFailTest(){
        //given
        insertStore();

        //when
        //then
        Assertions.assertThrows(StoreException.class,()-> storeService.getAllStore(12.0, 15.0,"스파게티"));

    }


    private void insertStore() {
        OwnerStore store=OwnerStore.builder()
                .address(new Address("서울시","첨당동","1234",12.0,15.0))
                .storeDescription("맛있다 맛있어 분식. 떡뽁이, 돈까스")
                .storeName("맛나 분식")
                .ownerId("test123")
                .closeTime(LocalTime.of(22,0))
                .openTime(LocalTime.of(11,0))
                .star(2.5)
                .build();

        OwnerStore store2=OwnerStore.builder()
                .address(new Address("서울시","첨당동","1234",20.0,18.0))
                .storeDescription("맛있다 맛있어 분식. 떡뽁이, 돈까스")
                .storeName("요리 품격")
                .ownerId("test123")
                .closeTime(LocalTime.of(22,0))
                .openTime(LocalTime.of(11,0))
                .star(2.5)
                .build();


        OwnerStore store3=OwnerStore.builder()
                .address(new Address("서울시","첨당동","1234",20.0,18.0))
                .storeDescription("맛있다 맛있어 분식. 떡뽁이, 돈까스")
                .storeName("요리 품격")
                .ownerId("test123")
                .closeTime(LocalTime.of(22,0))
                .openTime(LocalTime.of(11,0))
                .star(3.4)
                .build();
        storeRepository.save(store);
        storeRepository.save(store2);
        storeRepository.save(store3);
    }

    @Test
    void getStoreSuccess(){
        // given
        OwnerStore store=OwnerStore.builder()
                .address(new Address("서울시","첨당동","1234",12.0,15.0))
                .storeDescription("맛있다 맛있어 분식. 떡뽁이, 돈까스")
                .storeName("맛나 분식")
                .ownerId("test123")
                .closeTime(LocalTime.of(22,0))
                .openTime(LocalTime.of(11,0))
                .star(2.5)
                .build();
        storeRepository.save(store);

        // when
        StoreDetailInfo result = storeService.getStore(store.getId());

        // then
        assertEquals("맛나 분식", result.getStoreName());
    }

    @Test
    void getStoreFail(){
        // given
        OwnerStore store=OwnerStore.builder()
                .address(new Address("서울시","첨당동","1234",12.0,15.0))
                .storeDescription("맛있다 맛있어 분식. 떡뽁이, 돈까스")
                .storeName("맛나 분식")
                .ownerId("test123")
                .closeTime(LocalTime.of(22,0))
                .openTime(LocalTime.of(11,0))
                .star(2.5)
                .build();


        storeRepository.save(store);

        // when
        // then
        assertThrows(StoreException.class,()-> storeService.getStore(100L));
    }

}