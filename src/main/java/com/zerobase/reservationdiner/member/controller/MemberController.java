package com.zerobase.reservationdiner.member.controller;

import com.zerobase.reservationdiner.member.dto.MemberInfo;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
@RequestMapping("/member")
public class MemberController {

    @GetMapping("/login")
    public MemberInfo.Response login(@RequestBody MemberInfo.Request memberRequest){

    }

}
