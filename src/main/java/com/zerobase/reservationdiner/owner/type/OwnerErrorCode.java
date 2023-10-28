package com.zerobase.reservationdiner.owner.type;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public enum OwnerErrorCode {

    INVALID_STOREINFO("점포 가입 형식에 맞지 않습니다"),
    ALREADY_EXIST("해당 우편번호에 똑같은 점포 이름이 있습니다.");
    private final String description;
}
