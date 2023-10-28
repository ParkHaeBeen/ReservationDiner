package com.zerobase.reservationdiner.querydsltest;

import com.querydsl.jpa.impl.JPAQueryFactory;
import com.zerobase.reservationdiner.member.domain.Member;
import com.zerobase.reservationdiner.member.domain.QMember;
import com.zerobase.reservationdiner.member.type.MemberGrade;
import jakarta.persistence.EntityManager;
import jakarta.transaction.Transactional;
import lombok.extern.slf4j.Slf4j;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.ArrayList;
import java.util.List;

@SpringBootTest
@Transactional
@Slf4j
public class QuerydslApplicationTests {

    @Autowired
    EntityManager em;


    @Test
    void querydslContextLoadsTest(){
        //given
        List<String> roles=new ArrayList<>();
        roles.add(MemberGrade.ROLE_OWNER.toString());

        Member member = Member.builder()
                .memberId("test123")
                .memberPassword("test123")
                .memberName("test")
                .phoneNumber("010-1234-1234")
                .roles(roles)
                .build();
        em.persist(member);

        JPAQueryFactory query = new JPAQueryFactory(em);
        QMember qMember=QMember.member;

        //when
        Member result = query.selectFrom(qMember)
                .fetchOne();

        //then
        log.info("member grade = {} ",member.getRoles());
        Assertions.assertThat(member).isEqualTo(result);
        Assertions.assertThat(member.getId()).isEqualTo(result.getId());
    }

}
