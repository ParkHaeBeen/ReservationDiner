package com.zerobase.reservationdiner.member.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.zerobase.reservationdiner.member.dto.MemberInput;
import com.zerobase.reservationdiner.member.service.MemberService;
import com.zerobase.reservationdiner.member.type.MemberGrade;
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
import org.springframework.test.web.servlet.MockMvc;
import java.util.List;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@WebMvcTest(MemberController.class)
@Slf4j
@Import(WebSecurity.class)
class MemberControllerTest {

    @MockBean
    private  MemberService memberService;
    @MockBean
    private  TokenProvider tokenProvider;

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private ObjectMapper objectMapper;

    @Test
    @DisplayName("회원가입 성공")
    void registerSuccessTest() throws Exception {
        //given
        //when
        //then
        mockMvc.perform(post("/member/register")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(
                        MemberInput.builder()
                                .id("test123")
                                .password("test123")
                                .name("test")
                                .phoneNumber("010-1234-1234")
                                .roles(List.of(MemberGrade.ROLE_CUSTOMER.toString()))
                                .build()
                )))
                .andExpect(status().isOk())
                .andExpect(content().string("test"));
    }

    @Test
    @DisplayName("회원가입 형식 잘못으로 exception 발생")
    void registerFailTest() throws Exception {
        //given
        //when
        //then
        mockMvc.perform(post("/member/register")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(
                                MemberInput.builder()
                                        .id("test123")
                                        .name("test")
                                        .phoneNumber("010-1234-1234")
                                        .roles(List.of(MemberGrade.ROLE_CUSTOMER.toString()))
                                        .build()
                        )))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.errorMessage").value("가입 형식에 맞지 않습니다."))
                .andExpect(jsonPath("$.memberErrorCode").value("INVALIE_NEWMEMBERINFO"));

    }


}