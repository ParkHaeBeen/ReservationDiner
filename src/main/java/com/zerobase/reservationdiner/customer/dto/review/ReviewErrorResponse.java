package com.zerobase.reservationdiner.customer.dto.review;

import com.zerobase.reservationdiner.customer.type.ReviewErrorCode;
import lombok.*;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class ReviewErrorResponse {
    private ReviewErrorCode errorCode;
    private String errorMessage;
}
