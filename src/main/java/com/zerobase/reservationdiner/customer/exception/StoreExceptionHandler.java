package com.zerobase.reservationdiner.customer.exception;

import com.zerobase.reservationdiner.customer.dto.reservation.ReservationErrorResponse;
import com.zerobase.reservationdiner.customer.dto.review.ReviewErrorResponse;
import com.zerobase.reservationdiner.customer.dto.store.StoreErrorResponse;
import com.zerobase.reservationdiner.customer.exception.reservation.ReservationException;
import com.zerobase.reservationdiner.customer.exception.store.StoreException;
import com.zerobase.reservationdiner.customer.type.ReviewErrorCode;
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

    @ExceptionHandler(ReservationException.class)
    public ReservationErrorResponse handlerReservationException(ReservationException e){
        log.error("{} is occured",e.getErrorMessage());

        return ReservationErrorResponse.builder()
                .errorCode(e.getErrorCode())
                .errorMessage(e.getErrorMessage())
                .build();
    }

    @ExceptionHandler(ReviewException.class)
    public ReviewErrorResponse handlerReservationException(ReviewException e){
        log.error("{} is occured",e.getErrorMessage());

        return ReviewErrorResponse.builder()
                .errorCode(e.getReviewErrorCode())
                .errorMessage(e.getErrorMessage())
                .build();
    }

}
