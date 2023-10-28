package com.zerobase.reservationdiner.member.exception;

import com.zerobase.reservationdiner.member.type.MemberErrorCode;
import lombok.*;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class MemberException extends RuntimeException {
    private MemberErrorCode memberErrorCode;
    private String errorMessage;

    public MemberException(MemberErrorCode memberErrorCode){
        this.memberErrorCode = memberErrorCode;
        this.errorMessage= memberErrorCode.getDescription();
    }
}
