package com.zerobase.reservationdiner.member.service;

import com.zerobase.reservationdiner.member.domain.Member;
import com.zerobase.reservationdiner.member.dto.MemberInfo;
import com.zerobase.reservationdiner.member.dto.MemberInput;
import com.zerobase.reservationdiner.member.exception.MemberException;
import com.zerobase.reservationdiner.member.repository.MemberRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCrypt;
import org.springframework.stereotype.Service;

import static com.zerobase.reservationdiner.member.type.MemberErrorCode.*;

@Service
@Slf4j
@RequiredArgsConstructor
public class MemberServiceImpl implements MemberService{

    private final MemberRepository memberRepository;
    //private final PasswordEncoder passwordEncoder;

    @Override
    public UserDetails loadUserByUsername(String memberId) throws UsernameNotFoundException {
        return memberRepository.findByMemberId(memberId).orElseThrow(()->new MemberException(NO_EXIST_MEMBER));

    }

    @Override
    public void register(MemberInput memberInput) {
        boolean existMemberId = memberRepository.existsByMemberId(memberInput.getId());

        if(existMemberId){
            throw new MemberException(ARLEADY_EXIST_ID);
        }

        //passwordEncoder.encode(memberInput.getPassword());
        String encPassword = BCrypt.hashpw(memberInput.getPassword(), BCrypt.gensalt());
        memberInput.setPassword(encPassword);


        memberRepository.save(MemberInput.from(memberInput));

    }


    @Override
    public MemberInfo.Response authenticate(MemberInfo.Request request) {
        Member member = memberRepository.findByMemberId(request.getMemberId())
                .orElseThrow(() -> new MemberException(INVALID_MEMBERINFO));

        String encPassword = BCrypt.hashpw(request.getMemberPassword(), BCrypt.gensalt());

        if(!BCrypt.checkpw(request.getMemberPassword(),member.getPassword())){
            throw new MemberException(INVALID_MEMBERINFO);
        }

        return MemberInfo.Response.builder()
                .memberName(member.getMemberName())
                .roles(member.getRoles())
                .build();
    }
}
