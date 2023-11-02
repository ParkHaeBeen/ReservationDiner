package com.zerobase.reservationdiner.customer.exception.reservation;

import com.zerobase.reservationdiner.customer.type.ReservationErrorCode;
import lombok.*;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class ReservationException extends RuntimeException{
    private ReservationErrorCode errorCode;
    private String errorMessage;

    public ReservationException(ReservationErrorCode errorCode){
        this.errorCode=errorCode;
        this.errorMessage=errorCode.getDescription();
    }
}
