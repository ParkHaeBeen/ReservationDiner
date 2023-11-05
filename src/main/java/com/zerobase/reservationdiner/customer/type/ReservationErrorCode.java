package com.zerobase.reservationdiner.customer.type;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@RequiredArgsConstructor
public enum ReservationErrorCode {

    INVALID_ERROR("예약을 다시 진행해주세요"),
    INVALID_TIME("해당 예약 시간이 존재하지 않습니다"),
    INVALID_LOGIN("다시 로그인 진행해주세요"),
    ALREADY_RESERVE_TABLE("이미 예약이 되었습니다");

    private final String description;
}
