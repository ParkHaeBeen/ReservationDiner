package com.zerobase.reservationdiner.owner.service;

import com.zerobase.reservationdiner.owner.domain.OwnerStore;
import com.zerobase.reservationdiner.owner.dto.StoreInput;
import com.zerobase.reservationdiner.owner.exception.OwnerException;
import com.zerobase.reservationdiner.owner.repository.OwnerRepository;
import com.zerobase.reservationdiner.owner.type.OwnerErrorCode;
import jakarta.transaction.Transactional;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.annotation.Rollback;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
@Transactional
class OwnerServiceImplTest {

    @Autowired
    private OwnerRepository ownerRepository;

    @Autowired
    private OwnerService ownerService;

    @Test
    @Rollback(value = false)
    void registerSuccessTest(){
        //given
        StoreInput newStore = StoreInput.builder()
                .storeName("test 점포123")
                .city("서울")
                .street("아아동")
                .zipcode("12345")
                .description("맛있다.너무 맜있다.짱봉 팔아요")
                .build();
        //when
        ownerService.registerStore(newStore);

        Optional<OwnerStore> byStoreName = ownerRepository.findByStoreName(newStore.getStoreName());

        //then
        Assertions.assertNotNull(byStoreName);
        Assertions.assertEquals(newStore.getZipcode(),byStoreName.get().getZipcode());
    }

    @Test
    @Rollback(value = false)
    void registerFailByNameAndZipCodeTest(){
        //given
        StoreInput newStore = StoreInput.builder()
                .storeName("test 점포")
                .city("서울")
                .street("아아동")
                .zipcode("15477")
                .description("맛있다.너무맛있다 피자 팔아요 피자")
                .build();

        StoreInput failStore = StoreInput.builder()
                .storeName("test 점포")
                .city("서울")
                .street("어야동")
                .zipcode("15477")
                .description("맛있었다.치킨 빠삭하게 팔아요.")
                .build();
        //when
        ownerService.registerStore(newStore);

        //then
        Assertions.assertThrows(OwnerException.class,()->ownerService.registerStore(failStore));
    }
}