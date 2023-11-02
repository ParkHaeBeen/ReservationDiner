package com.zerobase.reservationdiner.customer.exception;

import com.zerobase.reservationdiner.customer.dto.store.StoreErrorResponse;
import com.zerobase.reservationdiner.customer.exception.store.StoreException;
import com.zerobase.reservationdiner.customer.type.StoreErrorCode;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@Slf4j
@RestControllerAdvice
public class StoreExceptionHandler {

    @ExceptionHandler(StoreException.class)
    public StoreErrorResponse handlerStoreException(StoreException e){
        log.error("{} is occured",e.getErrorMessage());

        return StoreErrorResponse.builder()
                .errorCode(e.getStoreErrorCode())
                .errorMessage(e.getErrorMessage())
                .build();
    }

    @ExceptionHandler(Exception.class)
    public StoreErrorResponse handleException(Exception e){
        log.error("{} is occured",e.getMessage());

        return StoreErrorResponse.builder()
                .errorMessage(StoreErrorCode.INTERNAL_SERVER_ERROR.getDescription())
                .errorCode(StoreErrorCode.INTERNAL_SERVER_ERROR)
                .build();
    }
}
