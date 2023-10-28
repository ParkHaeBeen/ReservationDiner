package com.zerobase.reservationdiner.owner.exception;

import com.zerobase.reservationdiner.owner.type.OwnerErrorCode;
import lombok.*;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class OwnerException extends RuntimeException{
    private OwnerErrorCode ownerErrorCode;
    private String errorMesaage;

    public OwnerException(OwnerErrorCode errorCode){
        this.ownerErrorCode=errorCode;
        this.errorMesaage=errorCode.getDescription();
    }
}
