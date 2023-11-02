package com.zerobase.reservationdiner.owner.type;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public enum ReservePermitErrorCode {
    NOT_EXIST_RESERVE("해당 예약은 존재하지 않습니다"),
    ALREADY_PERMIT("이미 예약을 허용하셨습니다"),
    ALREADY_CANCEL_BYCUSTOMER("이미 고객이 예약을 취소하셨습니다");
    private final String description;
}
