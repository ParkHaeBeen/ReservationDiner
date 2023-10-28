package com.zerobase.reservationdiner.member.dto;

import com.zerobase.reservationdiner.member.type.MemberErrorCode;
import lombok.*;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class ErrorResponse {
    private MemberErrorCode memberErrorCode;
    private String errorMessage;
}
