package com.zerobase.reservationdiner.querydsltest;

import com.querydsl.jpa.impl.JPAQueryFactory;
import com.zerobase.reservationdiner.member.domain.Member;
import com.zerobase.reservationdiner.member.domain.QMember;
import com.zerobase.reservationdiner.member.type.MemberGrade;
import jakarta.persistence.EntityManager;
import jakarta.transaction.Transactional;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest
@Transactional
public class QuerydslApplicationTests {

    @Autowired
    EntityManager em;


    @Test
    void querydslContextLoadsTest(){
        //given
        Member member = Member.builder()
                .memberId("test123")
                .memberPassword("test123")
                .memberName("test")
                .phoneNumber("010-1234-1234")
                .memberGrade(MemberGrade.CUSTOMER)
                .build();
        em.persist(member);

        JPAQueryFactory query = new JPAQueryFactory(em);
        QMember qMember=QMember.member;

        //when
        Member result = query.selectFrom(qMember)
                .fetchOne();

        //then
        Assertions.assertThat(member).isEqualTo(result);
        Assertions.assertThat(member.getId()).isEqualTo(result.getId());
    }

}
