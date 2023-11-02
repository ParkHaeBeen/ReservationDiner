package com.zerobase.reservationdiner.customer.exception.store;

import com.zerobase.reservationdiner.customer.type.StoreErrorCode;
import lombok.*;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class StoreException extends RuntimeException{
    private StoreErrorCode storeErrorCode;
    private String errorMessage;

    public StoreException(StoreErrorCode errorCode){
        this.storeErrorCode=errorCode;
        this.errorMessage=errorCode.getDescription();
    }

}
