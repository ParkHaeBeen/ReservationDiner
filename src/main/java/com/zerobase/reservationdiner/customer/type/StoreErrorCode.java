package com.zerobase.reservationdiner.customer.type;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@RequiredArgsConstructor
public enum StoreErrorCode {

    NOTFOUND_STORE("해당 음식점은 존재하지 않습니다"),
    INTERNAL_SERVER_ERROR("내부 서버오류로 관리자에게 문의해주세요"),
    ALREADY_RESERVE_TABLE("이미 예약이 되었습니다"),
    ALREADY_OPEN("이미 오픈 상태가 변경되었습니다"),
    INVALID_STOREINFO("유효한 상점정보가 아닙니다");
    private final String description;
}
