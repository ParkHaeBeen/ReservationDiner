package com.zerobase.reservationdiner.owner.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.zerobase.reservationdiner.common.dto.AddressDto;
import com.zerobase.reservationdiner.member.service.MemberService;
import com.zerobase.reservationdiner.owner.dto.ReserveInfo;
import com.zerobase.reservationdiner.owner.dto.StoreInput;
import com.zerobase.reservationdiner.owner.dto.StoreOpen;
import com.zerobase.reservationdiner.owner.dto.StoreRegister;
import com.zerobase.reservationdiner.owner.service.OwnerService;
import com.zerobase.reservationdiner.security.TokenProvider;
import com.zerobase.reservationdiner.security.WebSecurity;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.context.annotation.Import;
import org.springframework.http.MediaType;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.web.servlet.MockMvc;

import java.time.LocalTime;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.BDDMockito.given;
import static org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors.csrf;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest(OwnerController.class)
@Slf4j
@Import(WebSecurity.class)
class OwnerControllerTest {

    @MockBean
    private OwnerService ownerService;

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private ObjectMapper objectMapper;

    @MockBean
    private TokenProvider tokenProvider;

    @MockBean
    private MemberService memberService;

    @Test
    @DisplayName("점주 등급으로 음식점 등록 성공")
    @WithMockUser(username = "test123",roles = {"OWNER"})
    void registerSuccessTest() throws Exception{
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
                .description("떡복이,김밥, 돈까스 전문점입니다.")
                .openTime(LocalTime.of(10,0))
                .closeTime(LocalTime.of(20,0))
                .build();

        given(ownerService.registerStore(any()))
                .willReturn(StoreRegister.builder()
                        .storeName("맛나 분식")
                        .storeId(1L)
                        .build());

        //then,when
        mockMvc.perform(post("/owner/newstore")
                        .with(csrf())
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(newStore)))
                .andExpect(status().isOk())
                .andExpect(content().string("맛나 분식"));
    }

    @Test
    @DisplayName("소비자 등급으로 음식점 등록 실패")
    @WithMockUser(username = "test123",roles = {"CUSTOMER"})
    void registerFailTest() throws Exception{
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
                .description("떡복이,김밥, 돈까스 전문점입니다.")
                .openTime(LocalTime.of(10,0))
                .closeTime(LocalTime.of(20,0))
                .build();

        //then,when
        mockMvc.perform(post("/owner/newstore")
                        .with(csrf())
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(newStore)))
                .andExpect(status().isForbidden());

    }

    @Test
    @DisplayName("음식점 실오픈 성공 테스트")
    @WithMockUser(username = "test123",roles = {"OWNER"})
    void realOpenStatusSuccessTest() throws Exception {
        //given
        given(ownerService.registerStore(any()))
                .willReturn(StoreRegister.builder()
                        .storeName("맛나 분식")
                        .storeId(1L)
                        .build());

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
                .description("떡복이,김밥, 돈까스 전문점입니다.")
                .openTime(LocalTime.of(10,0))
                .closeTime(LocalTime.of(20,0))
                .build();

        StoreRegister storeRegister = ownerService.registerStore(newStore);


        //when
        StoreOpen open=StoreOpen.builder()
                .open(true)
                .ownerId(newStore.getOwnerId())
                .storeId(storeRegister.getStoreId())
                .build();
        //then
        mockMvc.perform(post("/owner/openstatus")
                        .with(csrf())
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(open)))
                .andExpect(status().isOk());
    }

    @Test
    @DisplayName("고객 예약 요청 수락 성공 테스트")
    @WithMockUser(username = "test123",roles = {"OWNER"})
    void permitReserve() throws Exception{
        //given
        ReserveInfo.Request request=ReserveInfo.Request.builder()
                .reservationId(1L)
                .permit(true)
                .build();
        //when
        //then
        mockMvc.perform(post("/owner/permitreserve")
                        .with(csrf())
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(request)))
                .andExpect(status().isOk());
    }
}