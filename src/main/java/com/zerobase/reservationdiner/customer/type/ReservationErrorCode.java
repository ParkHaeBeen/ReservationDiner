package com.zerobase.reservationdiner.customer.type;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@RequiredArgsConstructor
public enum ReservationErrorCode {

    INVALID_ERROR("예약을 다시 진행해주세요"), INVALID_TIME("해당 예약 시간이 존재하지 않습니다");

    private final String description;
}
