package com.zerobase.reservationdiner.owner.dto;

import lombok.*;

public class ReserveInfo {
    @Getter
    @Setter
    @AllArgsConstructor
    @NoArgsConstructor
    @Builder
    public static class Request{

        private Long reservationId;
        private Boolean permit;
    }

    static class Response{

    }
}
