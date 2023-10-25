package com.zerobase.reservationdiner.member.repository;

import com.zerobase.reservationdiner.member.domain.Member;

import java.util.Optional;

public interface CustomMemberRepository {

    Optional<Member> findByMemberName(String name);
}
