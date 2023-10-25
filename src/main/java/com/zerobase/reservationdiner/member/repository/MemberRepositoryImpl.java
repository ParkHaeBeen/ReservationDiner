package com.zerobase.reservationdiner.member.repository;

import com.querydsl.core.types.dsl.BooleanExpression;
import com.querydsl.jpa.impl.JPAQueryFactory;
import com.zerobase.reservationdiner.member.domain.Member;
import com.zerobase.reservationdiner.member.domain.QMember;
import jakarta.persistence.EntityManager;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import java.util.Optional;

import static com.zerobase.reservationdiner.member.domain.QMember.*;


public class MemberRepositoryImpl implements CustomMemberRepository{


    private final JPAQueryFactory queryFactory;

    public MemberRepositoryImpl(EntityManager em){
        this.queryFactory=new JPAQueryFactory(em);
    }

    @Override
    public Optional<Member> findByMemberName(String name) {
        return Optional.ofNullable(queryFactory.selectFrom(member)
                .where(memberNmaeEq(name))
                .fetchOne());
    }

    private BooleanExpression memberNmaeEq(String membername){
        return membername==null? null:member.memberName.eq(membername);
    }
}
