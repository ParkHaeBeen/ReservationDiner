package com.zerobase.reservationdiner.customer.controller;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.zerobase.reservationdiner.customer.dto.reservation.ReservationInfo;
import com.zerobase.reservationdiner.customer.service.reservation.ReservationService;
import com.zerobase.reservationdiner.customer.service.store.StoreService;
import com.zerobase.reservationdiner.security.TokenProvider;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.web.servlet.MockMvc;
import org.thymeleaf.model.IText;

import java.time.LocalDateTime;

import static org.junit.jupiter.api.Assertions.*;
import static org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors.csrf;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@WebMvcTest(CustomerController.class)
@Slf4j
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

    @Test
    @WithMockUser(username = "test123",roles = {"CUSTOMER"})
    void reservationStoreSuccessTest() throws Exception {
        //given
        ReservationInfo.Request request=ReservationInfo.Request.builder()
                .reservationDate(LocalDateTime.now())
                .customerCnt(2)
                .timeslotId(1L)
                .memberId("test123")
                .phoneNumber("010-1234-1234")
                .build();

        //when,then
        mockMvc.perform(post("/reservation")
                .with(csrf())
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(request)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.reservationDate").value(request.getReservationDate().toString()))
                .andExpect(jsonPath("$.customerCnt").value(request.getCustomerCnt()));

    }
}