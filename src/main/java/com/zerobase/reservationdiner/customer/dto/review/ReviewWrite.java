package com.zerobase.reservationdiner.customer.dto.review;

import com.zerobase.reservationdiner.customer.domain.Review;
import jakarta.validation.constraints.NotNull;
import lombok.*;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class ReviewWrite {
    @NotNull
    private String memberId;
    @NotNull
    private Long reservationId;

    @NotNull
    private String reviewName;

    @NotNull
    private String reviewDescription;

    public static Review of(ReviewWrite write){
        return Review.builder()
                .reviewDescription(write.reviewDescription)
                .reviewName(write.reviewName)
                .build();
    }
}
