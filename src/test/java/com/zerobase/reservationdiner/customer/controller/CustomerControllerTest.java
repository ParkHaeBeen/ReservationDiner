package com.zerobase.reservationdiner.customer.controller;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.zerobase.reservationdiner.customer.dto.CustomerSite;
import com.zerobase.reservationdiner.customer.dto.reservation.ReservationInfo;
import com.zerobase.reservationdiner.customer.dto.review.ReviewRole;
import com.zerobase.reservationdiner.customer.dto.store.StoreDetailInfo;
import com.zerobase.reservationdiner.customer.dto.store.StoreInfo;
import com.zerobase.reservationdiner.customer.service.reservation.ReservationService;
import com.zerobase.reservationdiner.customer.service.review.ReviewService;
import com.zerobase.reservationdiner.customer.service.store.StoreService;
import com.zerobase.reservationdiner.member.service.MemberService;
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
import org.springframework.security.test.context.support.WithAnonymousUser;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.web.servlet.MockMvc;
import org.thymeleaf.model.IText;

import java.time.LocalDateTime;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.*;
import static org.mockito.BDDMockito.given;
import static org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors.csrf;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@WebMvcTest(CustomerController.class)
@Slf4j
@Import(WebSecurity.class)
class CustomerControllerTest {

    @MockBean
    private ReservationService reservationService;
    @MockBean
    private StoreService storeService;

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private ObjectMapper objectMapper;

    @MockBean
    private TokenProvider tokenProvider;
    @MockBean
    private ReviewService reviewService;
    @MockBean
    private MemberService memberService;
    @Test
    @DisplayName("매장 목록 성공")
    @WithAnonymousUser
    void getAllStroeSuccessTest() throws Exception{
        //given
        CustomerSite site=new CustomerSite();
        site.setLatitude(15.0);
        site.setLatitude(12.0);
        site.setStoreName("맛나분식");

        List<StoreInfo> expectedStoreList = new ArrayList<>();
        expectedStoreList.add(new StoreInfo());
        expectedStoreList.add(new StoreInfo());

        given(storeService.getAllStore(anyDouble(),anyDouble(),anyString()))
                .willReturn(expectedStoreList);
        //when
        //then
        mockMvc.perform(get("/store")
                .with(csrf())
                        .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(site)))
                .andExpect(status().isOk());
    }

    @Test
    @DisplayName("매장 상세 성공")
    @WithAnonymousUser
    void getAStroeDetailSuccessTest() throws Exception{
        //given
        given(storeService.getStore(anyLong()))
                .willReturn(StoreDetailInfo.builder()
                        .storeId(1L)
                        .storeName("맛나분식")
                        .address(any())
                        .openTime(LocalTime.of(12,0))
                        .closeTime(LocalTime.of(20,0))
                        .description("맛잇ㄷ다 맛있어")
                        .build());
        //when
        //then
        mockMvc.perform(get("/store/detail/{storeId}",1L)
                        .with(csrf())
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk());
    }
    @Test
    @DisplayName("매장 상세 실패")
    @WithAnonymousUser
    void getAStroeDetailFailTest() throws Exception{
        //given
        given(storeService.getStore(anyLong()))
                .willReturn(null);
        //when
        //then
        mockMvc.perform(get("/store/detail/{storeId}",1L)
                        .with(csrf())
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(content().string(""));
    }
    @Test
    @WithMockUser(username = "test123",roles = {"CUSTOMER"})
    @DisplayName("매장 예약 성공")
    void reservationStoreSuccessTest() throws Exception {
        //given
        LocalDateTime now=LocalDateTime.now();
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");
        String formattedDateTime = now.format(formatter);

        ReservationInfo.Request request=ReservationInfo.Request.builder()
                .reservationDate(LocalDateTime.parse(formattedDateTime, formatter))
                .customerCnt(2)
                .timeslotId(1L)
                .memberId("test123")
                .phoneNumber("010-1234-1234")
                .build();

        given(reservationService.reservationStore(any()))
                .willReturn(ReservationInfo.Response.builder()
                        .reservationDate(request.getReservationDate())
                        .customerCnt(request.getCustomerCnt())
                        .build());

        //when,then
        mockMvc.perform(post("/reservation")
                .with(csrf())
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(request)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.customerCnt").value(request.getCustomerCnt()))
                .andExpect(jsonPath("$.reservationDate").value(LocalDateTime.parse(formattedDateTime, formatter).toString()));


    }

    @Test
    @WithAnonymousUser
    @DisplayName("매장 예약 로그인 하지 않아 실패")
    void reservationStoreFailTest() throws Exception {
        //given
        ReservationInfo.Request request=ReservationInfo.Request.builder()
                .reservationDate(LocalDateTime.now())
                .customerCnt(2)
                .timeslotId(1L)
                .memberId("test123")
                .phoneNumber("010-1234-1234")
                .build();

        given(reservationService.reservationStore(any()))
                .willReturn(ReservationInfo.Response.builder()
                        .reservationDate(request.getReservationDate())
                        .customerCnt(request.getCustomerCnt())
                        .build());

        //when,then
        mockMvc.perform(post("/reservation")
                        .with(csrf())
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(request)))
                .andExpect(status().isForbidden());

    }

    @Test
    @WithMockUser(username = "test123",roles = {"CUSTOMER"})
    @DisplayName("매장 방문하여 리뷰 작성 가능")
    void checkPossibleReviewSuccessTest() throws Exception{
        //given
        given(reviewService.checkReviewRole(any()))
                .willReturn(true);

        ReviewRole reviewRole=ReviewRole.builder()
                .reservationId(1L)
                .memberId("test123")
                .build();
        //when
        //then
        mockMvc.perform(get("/review")
                .with(csrf())
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(reviewRole)))
                .andExpect(status().isOk())
                .andExpect(content().string("ok"));
    }

    @Test
    @WithMockUser(username = "test123",roles = {"CUSTOMER"})
    @DisplayName("매장 방문하지 않아 리뷰 작성 불가능")
    void checkPossibleReviewFailTest() throws Exception{
        //given
        given(reviewService.checkReviewRole(any()))
                .willReturn(false);

        ReviewRole reviewRole=ReviewRole.builder()
                .reservationId(1L)
                .memberId("test123")
                .build();
        //when
        //then
        mockMvc.perform(get("/review")
                        .with(csrf())
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(reviewRole)))
                .andExpect(status().isBadRequest());
    }


}