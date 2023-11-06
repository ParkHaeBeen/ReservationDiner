package com.zerobase.reservationdiner.common.dto;

import lombok.*;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class CommonErrorResponse {
    private String errorMessage;
}
