package com.zerobase.reservationdiner.kiosk.dto;

import com.zerobase.reservationdiner.kiosk.type.KioskErrorCode;
import com.zerobase.reservationdiner.member.type.MemberErrorCode;
import lombok.*;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class ErrorResponse {
    private KioskErrorCode kioskErrorCode;
    private String errorMessage;
}
