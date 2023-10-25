package com.zerobase.reservationdiner.member.service;

import com.zerobase.reservationdiner.member.dto.MemberInfo;
import com.zerobase.reservationdiner.member.dto.MemberInput;
import org.springframework.security.core.userdetails.UserDetailsService;

public interface MemberService extends UserDetailsService {

    void register(MemberInput memberInput);

    //로그인할때 검증을 위한 메소드
    MemberInfo.Response authenticate(MemberInfo.Request request);
}
