package com.zerobase.reservationdiner.member.controller;

import com.zerobase.reservationdiner.member.dto.MemberInfo;
import com.zerobase.reservationdiner.member.dto.MemberInput;
import com.zerobase.reservationdiner.member.exception.MemberException;
import com.zerobase.reservationdiner.member.service.MemberService;
import com.zerobase.reservationdiner.member.type.MemberErrorCode;
import com.zerobase.reservationdiner.security.TokenProvider;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import static com.zerobase.reservationdiner.member.type.MemberErrorCode.*;

@RestController
@RequiredArgsConstructor
@RequestMapping("/member")
@Slf4j
public class MemberController {

    private final MemberService memberService;
    private final TokenProvider tokenProvider;

    @PostMapping("/login")
    public ResponseEntity<?> login(@Valid @RequestBody MemberInfo.Request loginInfo,BindingResult result){
        checkBindResultErrors(result, INVALID_MEMBERINFO);

        MemberInfo.Response member = memberService.authenticate(loginInfo);
        String token = tokenProvider.generateToken(member.getMemberName(), member.getRoles());
        log.info("login success");
        return ResponseEntity.ok(token);
    }


    @PostMapping("/register")
    public ResponseEntity<?> register(@Valid @RequestBody MemberInput memberInput, BindingResult result){
        checkBindResultErrors(result, INVALIE_NEWMEMBERINFO);

        memberService.register(memberInput);
        return ResponseEntity.ok(memberInput);
    }

    private static void checkBindResultErrors(BindingResult result, MemberErrorCode invalidMemberinfo) {
        if (result.hasErrors()) {
            throw new MemberException(invalidMemberinfo);
        }
    }


}
