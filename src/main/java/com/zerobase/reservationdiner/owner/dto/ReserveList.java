package com.zerobase.reservationdiner.owner.dto;

import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.*;

import java.time.LocalDateTime;

public class ReserveList {
    @Getter
    @Setter
    @AllArgsConstructor
    @NoArgsConstructor
    @Builder
    public static class Request{

        @NotNull
        private String storeName;
        @NotNull
        private String ownerId;

        @NotNull
        private LocalDateTime startDate;
        @NotNull
        private LocalDateTime endDate;

    }
    @Getter
    @Setter
    @AllArgsConstructor
    @NoArgsConstructor
    @Builder
    public static class Response{
        private String storeName;
        private String customerName;
        private String phoneNumber;
        private Integer customerCnt;
        private LocalDateTime reserveDate;
    }
}
