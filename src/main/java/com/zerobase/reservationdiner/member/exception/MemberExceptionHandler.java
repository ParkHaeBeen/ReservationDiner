package com.zerobase.reservationdiner.member.exception;

import com.zerobase.reservationdiner.member.dto.ErrorResponse;
import com.zerobase.reservationdiner.member.exception.MemberException;
import com.zerobase.reservationdiner.member.type.MemberErrorCode;
import com.zerobase.reservationdiner.owner.exception.OwnerException;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@Slf4j
@RestControllerAdvice
public class MemberExceptionHandler{

    @ExceptionHandler(MemberException.class)
    public ErrorResponse handleMemberException(MemberException e){
        log.error("{} is occured",e.getErrorMessage());

        return ErrorResponse.builder()
                        .memberErrorCode(e.getMemberErrorCode())
                        .errorMessage(e.getErrorMessage())
                        .build();
    }


}
