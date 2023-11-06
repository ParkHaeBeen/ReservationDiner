package com.zerobase.reservationdiner.common.globalexception;

import com.zerobase.reservationdiner.common.dto.CommonErrorResponse;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@Slf4j
@RestControllerAdvice
public class GlobalExceptionHandler {

    @ExceptionHandler(Exception.class)
    public CommonErrorResponse hanlderException(Exception e){
        log.error("{} is occured",e.getMessage());

        return CommonErrorResponse.builder()
                .errorMessage(e.getMessage())
                .build();
    }
}
