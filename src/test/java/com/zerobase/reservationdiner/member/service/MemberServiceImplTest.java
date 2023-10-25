package com.zerobase.reservationdiner.member.service;

import com.zerobase.reservationdiner.member.domain.Member;
import com.zerobase.reservationdiner.member.dto.MemberInput;
import com.zerobase.reservationdiner.member.repository.MemberRepository;

import com.zerobase.reservationdiner.member.type.MemberGrade;
import jakarta.transaction.Transactional;
import org.junit.jupiter.api.Assertions;
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
    @Rollback(value = false)
    void registerTest(){
        //given

        List<MemberGrade> roles=new ArrayList<>();
        roles.add(MemberGrade.OWNER);

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

}