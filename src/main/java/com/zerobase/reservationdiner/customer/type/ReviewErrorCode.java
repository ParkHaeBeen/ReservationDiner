package com.zerobase.reservationdiner.customer.type;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@RequiredArgsConstructor
public enum ReviewErrorCode {
    INVALID_MEMBERID("해당 아이디가 존재하지 않습니다"),
    INVALID_RESERVATION("해당 예약이 유효하지 않습니다"),
    NOT_VISTIT_STORE("해당 음식점에 방문하지 않으셨습니다"),
    NOT_MATCH_MEMBERID("예약 아이디와 매치하지 않습니다"),
    ALREADY_WRITE_REVIEW("이미 리뷰를 작성하셨습니다");

    private final String description;

}
