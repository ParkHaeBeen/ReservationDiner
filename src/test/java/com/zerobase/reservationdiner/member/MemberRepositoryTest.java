package com.zerobase.reservationdiner.member;

import com.zerobase.reservationdiner.member.domain.Member;
import com.zerobase.reservationdiner.member.repository.MemberRepository;
import com.zerobase.reservationdiner.member.type.MemberGrade;
import jakarta.persistence.EntityManager;
import jakarta.transaction.Transactional;
import lombok.extern.slf4j.Slf4j;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.annotation.Rollback;

import java.util.Optional;

@SpringBootTest
@Slf4j
@Transactional
public class MemberRepositoryTest {
    @Autowired
    EntityManager em;

    @Autowired
    MemberRepository memberRepository;

    @Test
    @Rollback(value = false)
    void memberRepositoryTest(){
        //given
        Member member = Member.builder()
                .memberId("test123")
                .memberPassword("test123")
                .memberName("test")
                .phoneNumber("010-1234-1234")
                .memberGrade(MemberGrade.CUSTOMER)
                .build();
        em.persist(member);

        //when
        Optional<Member> memberById = memberRepository.findByMemberId(member.getMemberId());
        Optional<Member> memberByName = memberRepository.findByMemberName(member.getMemberName());

        //then
        log.info("insert Member = {}",member);
        log.info("from Id = {}",memberById.get());
        log.info("from Naeme = {}",memberByName.get());
        Assertions.assertThat(memberById.get()).isEqualTo(member);
        Assertions.assertThat(memberByName.get()).isEqualTo(member);

    }
}
