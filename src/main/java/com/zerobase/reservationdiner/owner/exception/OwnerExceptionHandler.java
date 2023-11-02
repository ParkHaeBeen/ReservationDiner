package com.zerobase.reservationdiner.owner.exception;

import com.zerobase.reservationdiner.member.dto.ErrorResponse;
import com.zerobase.reservationdiner.owner.dto.OwnerErrorResponse;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@Slf4j
@RestControllerAdvice
public class OwnerExceptionHandler {

    @ExceptionHandler(OwnerException.class)
    public OwnerErrorResponse handleOwnerException(OwnerException e){
        log.error("{} is occured",e.getMessage());
        log.error("{} is occured",e.getErrorMesaage());

        return OwnerErrorResponse.builder()
                .ownerErrorCode(e.getOwnerErrorCode())
                .errorMessage(e.getErrorMesaage())
                .build();
    }
}
