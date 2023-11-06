package com.zerobase.reservationdiner.kiosk.exception;

import com.zerobase.reservationdiner.kiosk.dto.ErrorResponse;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@Slf4j
@RestControllerAdvice
public class KisokExceptionHandler {
    @ExceptionHandler(KioskException.class)
    public ErrorResponse handelKioskException(KioskException e){
        log.error("{} is occured",e.getErrorMessage());

        return ErrorResponse.builder()
                .errorMessage(e.getErrorMessage())
                .kioskErrorCode(e.getKioskErrorCode())
                .build();
    }
}
