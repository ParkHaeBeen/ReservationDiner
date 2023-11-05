package com.zerobase.reservationdiner.kiosk.dto;

import jakarta.validation.constraints.NotNull;
import lombok.*;

import java.time.LocalDateTime;


public class ArriveInfo {
    @Getter
    @Setter
    @AllArgsConstructor
    @NoArgsConstructor
    @Builder
    public static class Request{
        @NotNull
        private String memberId;
        @NotNull
        private Long ownerStoreId;

        private LocalDateTime arriveTime=LocalDateTime.now();
    }

    @Getter
    @Setter
    @AllArgsConstructor
    @NoArgsConstructor
    @Builder
    public static class Response{
        private String memberName;
        private LocalDateTime timeSlotTime;
        private Integer customerCnt;
        private Long timeSlotId;
    }

}
