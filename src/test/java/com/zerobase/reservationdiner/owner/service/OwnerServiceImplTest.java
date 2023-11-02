package com.zerobase.reservationdiner.owner.service;

import com.zerobase.reservationdiner.owner.domain.OwnerStore;
import com.zerobase.reservationdiner.common.dto.AddressDto;
import com.zerobase.reservationdiner.owner.dto.StoreInput;
import com.zerobase.reservationdiner.owner.exception.OwnerException;
import com.zerobase.reservationdiner.owner.repository.OwnerRepository;
import jakarta.transaction.Transactional;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.time.LocalTime;
import java.util.Optional;

@SpringBootTest
@Transactional
class OwnerServiceImplTest {

    @Autowired
    private OwnerRepository ownerRepository;

    @Autowired
    private OwnerService ownerService;

    @Test
    void registerSuccessTest(){
        //given
        AddressDto address= AddressDto.builder()
                .city("서울")
                .street("street1")
                .zipcode("1485")
                .lat(127.0)
                .lnt(45.0)
                .build();

        StoreInput newStore=StoreInput.builder()
                .address(address)
                .ownerId("test123")
                .storeName("맛나 분식")
                .openTime(LocalTime.of(8,0))
                .closeTime(LocalTime.of(22,0))
                .description("떡복이,김밥, 돈까스 전문점입니다.")
                .build();

        //when
        ownerService.registerStore(newStore);

        Optional<OwnerStore> byStoreName = ownerRepository.findByStoreName(newStore.getStoreName());

        //then
        Assertions.assertNotNull(byStoreName);
        Assertions.assertEquals(newStore.getAddress().getZipcode(),byStoreName.get().getAddress().getZipcode());
    }

    @Test
    void registerFailByNameAndZipCodeTest(){
        //given
        AddressDto address= AddressDto.builder()
                .city("서울")
                .street("street1")
                .zipcode("1485")
                .lat(127.0)
                .lnt(45.0)
                .build();

        StoreInput newStore=StoreInput.builder()
                .address(address)
                .ownerId("test123")
                .storeName("맛나 분식")
                .openTime(LocalTime.of(8,0))
                .closeTime(LocalTime.of(22,0))
                .description("떡복이,김밥, 돈까스 전문점입니다.")
                .build();

        StoreInput failStore = StoreInput.builder()
                .storeName("맛나 분식")
                .ownerId("test123")
                .address(address)
                .description("맛있었다.치킨 빠삭하게 팔아요.")
                .openTime(LocalTime.of(8,0))
                .closeTime(LocalTime.of(22,0))
                .build();
        //when
        ownerService.registerStore(newStore);

        //then
        Assertions.assertThrows(OwnerException.class,()->ownerService.registerStore(failStore));
    }
}