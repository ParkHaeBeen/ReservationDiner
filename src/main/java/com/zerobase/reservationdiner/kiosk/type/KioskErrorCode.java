package com.zerobase.reservationdiner.kiosk.type;

import com.zerobase.reservationdiner.member.exception.MemberException;
import com.zerobase.reservationdiner.member.type.MemberErrorCode;
import lombok.AllArgsConstructor;
import lombok.Getter;
import org.springframework.validation.BindingResult;
@Getter
@AllArgsConstructor
public enum KioskErrorCode {
    INVALID_KIOSK_INFO("아이디를 다시 입력해주세요"),
    NOT_FOUND_MEMBER("해당 고객을 찾을 수 없습니다"),
    INVALID_STORE("매장 매니저에게 문의해주세요"),
    LATE_FOR_CHECK("10분 이후에 도착하셔서 매장 매니저에게 문의해주셔야합니다"),
    ARLEADY_CHECK("이미 방문체크하셨습니다"),
    NOT_EXIST_TIMESLOT("해당 예약이 존재하지 앖습니다");

    private final String description;
}
