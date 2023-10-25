package com.zerobase.member.type;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public enum ErrorCode {
    INVALID_MEMBERINFO("아이디와 비밀번호를 확인해주세요");


    private final String description;


}
