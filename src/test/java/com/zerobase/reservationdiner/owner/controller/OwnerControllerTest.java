package com.zerobase.reservationdiner.owner.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.zerobase.reservationdiner.common.dto.AddressDto;
import com.zerobase.reservationdiner.owner.dto.StoreInput;
import com.zerobase.reservationdiner.owner.service.OwnerService;
import com.zerobase.reservationdiner.security.TokenProvider;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.web.servlet.MockMvc;

import java.time.LocalTime;

import static org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors.csrf;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest(OwnerController.class)
@Slf4j
class OwnerControllerTest {

    @MockBean
    private OwnerService ownerService;

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private ObjectMapper objectMapper;

    @MockBean
    private TokenProvider tokenProvider;

    @Test
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


        //then,when
        mockMvc.perform(post("/owner/newstore")
                        .with(csrf())
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(newStore)))
                .andExpect(status().isOk())
                .andExpect(content().string("맛나 분식"));
    }


}