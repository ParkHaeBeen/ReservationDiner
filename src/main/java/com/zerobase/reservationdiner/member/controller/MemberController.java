package com.zerobase.reservationdiner.member.controller;

import com.zerobase.member.type.ErrorCode;
import com.zerobase.reservationdiner.member.dto.MemberInfo;
import com.zerobase.reservationdiner.member.exception.MemberException;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import static com.zerobase.member.type.ErrorCode.*;

@RestController
@RequiredArgsConstructor
@RequestMapping("/member")
public class MemberController {

    @GetMapping("/login")
    public MemberInfo.Response login(@Valid @RequestBody MemberInfo.Request memberRequest
                                        , BindingResult bindingResult){
        if(bindingResult.hasErrors()){
            throw new MemberException(INVALID_MEMBERINFO);
        }

        return null;
    }


}
