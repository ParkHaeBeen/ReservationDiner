package com.zerobase.reservationdiner.customer.exception;

import com.zerobase.reservationdiner.customer.type.ReviewErrorCode;
import lombok.*;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class ReviewException extends RuntimeException{
    private ReviewErrorCode reviewErrorCode;
    private String errorMessage;

    public ReviewException(ReviewErrorCode errorCode){
        this.reviewErrorCode=errorCode;
        this.errorMessage=errorCode.getDescription();
    }
}
