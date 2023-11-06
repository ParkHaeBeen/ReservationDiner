package com.zerobase.reservationdiner.customer.service.review;

import com.zerobase.reservationdiner.customer.dto.review.ReviewRole;
import com.zerobase.reservationdiner.customer.dto.review.ReviewWrite;

public interface ReviewService {

    boolean checkReviewRole(ReviewRole reviewRole);

    void insertReview(ReviewWrite write);
}
