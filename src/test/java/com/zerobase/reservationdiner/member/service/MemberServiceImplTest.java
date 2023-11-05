package com.zerobase.reservationdiner.member.service;

import com.zerobase.reservationdiner.member.domain.Member;
import com.zerobase.reservationdiner.member.dto.MemberInfo;
import com.zerobase.reservationdiner.member.dto.MemberInput;
import com.zerobase.reservationdiner.member.exception.MemberException;
import com.zerobase.reservationdiner.member.repository.MemberRepository;

import com.zerobase.reservationdiner.member.type.MemberGrade;
import jakarta.annotation.PostConstruct;
import jakarta.transaction.Transactional;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.annotation.Rollback;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;



@SpringBootTest
@Transactional
class MemberServiceImplTest {

    @Autowired
    private MemberService memberService;
    @Autowired
    private MemberRepository memberRepository;


    @Test
    @DisplayName("회원 가입 서비스 성공")
    void registerSuccessTest(){
        //given
        List<String> roles=new ArrayList<>();
        roles.add(MemberGrade.ROLE_OWNER.toString());

        MemberInput member=MemberInput.builder()
                .roles(roles)
                .name("test")
                .password("12345")
                .id("test123")
                .phoneNumber("010-1234-1234")
                .build();

        //when
        memberService.register(member);

        Optional<Member> findMember = memberRepository.findByMemberId(member.getId());
        //then
        Assertions.assertNotNull(findMember.get());
        Assertions.assertEquals(member.getId(),findMember.get().getMemberId());
    }

    @Test
    @DisplayName("이미 존재하는 아이디로 회원가입 실패")
    void registerFailByAlreadyExistIdTest(){
        //given
        List<String> roles=new ArrayList<>();
        roles.add(MemberGrade.ROLE_OWNER.toString());

        MemberInput member1=MemberInput.builder()
                .roles(roles)
                .name("test")
                .password("12345")
                .id("test123")
                .phoneNumber("010-1234-1234")
                .build();

        MemberInput member2=MemberInput.builder()
                .roles(roles)
                .name("test")
                .password("12345")
                .id("test123")
                .phoneNumber("010-1234-1234")
                .build();


        //when
        memberService.register(member2);

        //then
        Assertions.assertThrows(MemberException.class,()->memberService.register(member2));
    }

    @Test
    @DisplayName("로그인 서비스 성공")
    void authenticateSuccessTest(){
        //given
        MemberInput member = insertMember();
        MemberInfo.Request login=new MemberInfo.Request("test123","12345");

        //when
        MemberInfo.Response response = memberService.authenticate(login);

        //then
        Assertions.assertEquals(member.getName(),response.getMemberName());
        Assertions.assertEquals(member.getRoles(),response.getRoles());
    }

    @Test
    @DisplayName("로그인 서비스 아이디 맞지 않아 실패")
    void authenticateFailNotIdTest(){
        //given
        insertMember();
        MemberInfo.Request login=new MemberInfo.Request("123123","12345");

        //when
        //then
        Assertions.assertThrows(MemberException.class,()->memberService.authenticate(login));

    }
    @Test
    @DisplayName("로그인 서비스 비밀번호 맞지 않아 실패")
    void authenticateFailNotMathPasswordTest(){
        //given
        insertMember();
        MemberInfo.Request login=new MemberInfo.Request("test123","1245");

        //when
        //then
        Assertions.assertThrows(MemberException.class,()->memberService.authenticate(login));

    }

    MemberInput insertMember(){
        List<String> roles=new ArrayList<>();
        roles.add(MemberGrade.ROLE_OWNER.toString());

        MemberInput member=MemberInput.builder()
                .roles(roles)
                .name("test")
                .password("12345")
                .id("test123")
                .phoneNumber("010-1234-1234")
                .build();

        memberService.register(member);

        return member;
    }
}