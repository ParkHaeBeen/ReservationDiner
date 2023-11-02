package com.zerobase.reservationdiner.owner.exception;

import com.zerobase.reservationdiner.owner.type.ReservePermitErrorCode;
import lombok.*;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class ReservePermitException extends RuntimeException{
    private ReservePermitErrorCode errorCode;
    private String errorMessage;

    public ReservePermitException(ReservePermitErrorCode errorCode){
        this.errorCode=errorCode;
        this.errorMessage=errorCode.getDescription();
    }
}
