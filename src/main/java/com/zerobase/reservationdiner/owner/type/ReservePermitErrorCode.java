package com.zerobase.reservationdiner.owner.type;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public enum ReservePermitErrorCode {
    NOT_EXIST_RESERVE("해당 예약은 존재하지 않습니다"),
    ALREADY_PERMIT("이미 예약을 허용하셨습니다"),
    ALREADY_CANCEL("이미 예약을 취소하셨습니다"),
    NOT_EXIST_TIME("해당 시간에는 예약 불가능합니다"),
    ALREADY_PERMIT_OTHER_CUSTOMER("다른 고객이 먼저 예약을 하셨습니");
    private final String description;
}
