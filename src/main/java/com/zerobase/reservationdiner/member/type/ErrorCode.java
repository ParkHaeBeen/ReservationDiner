package com.zerobase.reservationdiner.member.type;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public enum ErrorCode {
    INVALID_MEMBERINFO("아이디와 비밀번호를 확인해주세요."),
    INVALIE_NEWMEMBERINFO("가입 형식에 맞지 않습니다."),
    ARLEADY_EXIST_ID("이미 존재하는 아이디입니다."),
    NO_EXIST_MEMBER("회원 정보가 존재하지 않습니다."),
    NOT_MATCH_PASSWORD("비밀번호가 일치하지 않습니다."),
    INTERNAL_SERVER_ERROR("내부 서버오류로 관리자에게 문의해주세요");

    private final String description;


}
