package com.zerobase.reservationdiner.owner.service;

import com.zerobase.reservationdiner.member.domain.Member;
import com.zerobase.reservationdiner.member.repository.MemberRepository;
import com.zerobase.reservationdiner.member.type.MemberGrade;
import com.zerobase.reservationdiner.owner.domain.OwnerStore;
import com.zerobase.reservationdiner.common.dto.AddressDto;
import com.zerobase.reservationdiner.owner.dto.StoreInput;
import com.zerobase.reservationdiner.owner.dto.StoreOpen;
import com.zerobase.reservationdiner.owner.exception.OwnerException;
import com.zerobase.reservationdiner.owner.repository.owner.OwnerRepository;
import jakarta.transaction.Transactional;
import org.junit.jupiter.api.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.time.LocalTime;
import java.util.List;
import java.util.Optional;

@SpringBootTest
@Transactional
class OwnerServiceImplTest {

    @Autowired
    private OwnerRepository ownerRepository;

    @Autowired
    private OwnerService ownerService;

    @Autowired
    private MemberRepository memberRepository;

    @BeforeEach
    void beforeInsertMember(){
        Member member = Member.builder()
                .memberId("test123")
                .memberPassword("12345")
                .memberName("test")
                .roles(List.of(MemberGrade.ROLE_OWNER.toString()))
                .phoneNumber("010-1234-1234")
                .build();

        memberRepository.save(member);
    }

    @Test
    @DisplayName("상점 등록 성공")
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
    @DisplayName("상점 등록 실패 - 상점이름,우편번호 같은 상점 존재하여")
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


    @Test
    @DisplayName("상점 오픈 및 첫 오픈에 따라 예약 시간표 insert")
    void openAndTimeSoltAdd(){
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

        ownerService.registerStore(newStore);

        Optional<OwnerStore> byStoreName = ownerRepository.findByStoreName(newStore.getStoreName());

        StoreOpen storeOpen=StoreOpen.builder()
                .ownerId("test123")
                .open(true)
                .storeId(byStoreName.get().getId())
                .build();
        //when
        ownerService.openAndTimeSlotAdd(storeOpen);
        //then
        OwnerStore openStore = ownerRepository.findById(byStoreName.get().getId()).get();
        Assertions.assertNotNull(openStore);
        Assertions.assertEquals(openStore.getOpenStatus(),true);
    }

    @Test
    @DisplayName("이미 오픈 상태로 바뀌어 exception 발생")
    void openAndTimeSlotAddFailByAlreadyOpen(){
        //given
        Optional<Member> member = memberRepository.findByMemberId("test123");
        OwnerStore ownerStore=OwnerStore.builder()
                .owner(member.get())
                .storeName("맛나 분식")
                .storeDescription("맛잇다 맛잇어!!!")
                .openTime(LocalTime.of(10,0))
                .closeTime(LocalTime.of(20,0))
                .openStatus(true)
                .build();

        ownerRepository.save(ownerStore);

        Optional<OwnerStore> byStoreName = ownerRepository.findByStoreName(ownerStore.getStoreName());
        //when
        StoreOpen storeOpen=StoreOpen.builder()
                .ownerId("test123")
                .open(true)
                .storeId(byStoreName.get().getId())
                .build();

        //then
        Assertions.assertThrows(OwnerException.class,()->ownerService.openAndTimeSlotAdd(storeOpen));
    }


}