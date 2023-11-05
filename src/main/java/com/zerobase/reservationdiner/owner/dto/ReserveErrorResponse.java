package com.zerobase.reservationdiner.owner.dto;

import com.zerobase.reservationdiner.owner.type.ReservePermitErrorCode;
import lombok.*;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class ReserveErrorResponse {
    private ReservePermitErrorCode reservePermitErrorCode;
    private String errorMessage;
}
