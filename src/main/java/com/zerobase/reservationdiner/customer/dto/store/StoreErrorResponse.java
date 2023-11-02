package com.zerobase.reservationdiner.customer.dto.store;

import com.zerobase.reservationdiner.customer.type.StoreErrorCode;
import lombok.*;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class StoreErrorResponse {
    private StoreErrorCode errorCode;
    private String errorMessage;
}
