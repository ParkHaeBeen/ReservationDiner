package com.zerobase.reservationdiner.customer.dto.review;

import jakarta.validation.constraints.NotNull;
import lombok.*;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class ReviewRole {
    @NotNull
    private String memberId;
    @NotNull
    private Long reservationId;
}
