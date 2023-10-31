package com.zerobase.reservationdiner.owner.dto;

import com.zerobase.reservationdiner.owner.type.OwnerErrorCode;
import lombok.*;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class OwnerErrorResponse {
    private OwnerErrorCode ownerErrorCode;
    private String errorMessage;
}
