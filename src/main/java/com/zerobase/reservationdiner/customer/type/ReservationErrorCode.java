package com.zerobase.reservationdiner.customer.type;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@RequiredArgsConstructor
public enum ReservationErrorCode {

    INVALID_ERROR("예약을 다시 진행해주세요");

    private final String description;
}
