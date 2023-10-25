package com.zerobase.reservationdiner.member.exception;

import com.zerobase.reservationdiner.member.type.ErrorCode;
import lombok.*;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class MemberException extends RuntimeException {
    private ErrorCode errorCode;
    private String errorMessage;

    public MemberException(ErrorCode errorCode){
        this.errorCode=errorCode;
        this.errorMessage=errorCode.getDescription();
    }
}
