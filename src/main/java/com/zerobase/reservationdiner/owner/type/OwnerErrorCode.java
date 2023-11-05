package com.zerobase.reservationdiner.owner.type;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public enum OwnerErrorCode {

    INVALID_STOREINFO("형식에 맞쳐주세요"),
    ALREADY_EXIST("해당 우편번호에 똑같은 점포 이름이 있습니다."),
    NOEXIST_OWNER("해당 레스토랑은 존재하지 않습니다"),
    ALREADY_OPEN("이미 오픈 되었습니다");
    private final String description;
}
