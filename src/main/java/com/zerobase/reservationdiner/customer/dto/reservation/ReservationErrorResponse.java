package com.zerobase.reservationdiner.customer.dto.reservation;

import com.zerobase.reservationdiner.customer.type.ReservationErrorCode;
import lombok.*;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class ReservationErrorResponse {
    private ReservationErrorCode errorCode;
    private String errorMessage;
}
