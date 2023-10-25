package com.zerobase.reservationdiner.member.repository;

import com.zerobase.reservationdiner.member.domain.Member;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface MemberRepository extends JpaRepository<Member,Long> ,CustomMemberRepository{

    Optional<Member> findByMemberId(String memberId);
}
