package com.zerobase.reservationdiner.member.exception;

import com.zerobase.reservationdiner.member.dto.ErrorResponse;
import com.zerobase.reservationdiner.member.type.ErrorCode;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@Slf4j
@RestControllerAdvice
public class MemberExceptionHandler{

    @ExceptionHandler(MemberException.class)
    public ErrorResponse handleMemberException(MemberException e){
        log.error("{} is occured",e.getErrorCode());

        return ErrorResponse.builder()
                        .errorCode(e.getErrorCode())
                        .errorMessage(e.getErrorMessage())
                        .build();
    }

    @ExceptionHandler(Exception.class)
    public ErrorResponse handleException(Exception e){
        log.error("{} is occured",e.getMessage());

        return ErrorResponse.builder()
                .errorMessage(ErrorCode.INTERNAL_SERVER_ERROR.getDescription())
                .errorCode(ErrorCode.INTERNAL_SERVER_ERROR)
                .build();
    }

}
