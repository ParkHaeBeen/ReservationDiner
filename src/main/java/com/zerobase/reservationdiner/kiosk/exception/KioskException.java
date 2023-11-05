package com.zerobase.reservationdiner.kiosk.exception;

import com.zerobase.reservationdiner.kiosk.type.KioskErrorCode;
import lombok.*;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class KioskException extends RuntimeException {
    private KioskErrorCode kioskErrorCode;
    private String errorMessage;

    public KioskException(KioskErrorCode errorCode){
        this.kioskErrorCode=errorCode;
        this.errorMessage=errorCode.getDescription();
    }

}
