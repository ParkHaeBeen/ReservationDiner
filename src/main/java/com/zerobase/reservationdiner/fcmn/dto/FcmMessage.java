package com.zerobase.reservationdiner.fcmn.dto;

import lombok.*;

import java.time.LocalDateTime;


public class FcmMessage {

    @Getter
    @Setter
    @AllArgsConstructor
    @NoArgsConstructor
    @Builder
    public static class Request{
        private String targetToken;
        private String userName;
        private LocalDateTime reserveTime;

    }

    @Getter
    @Setter
    @AllArgsConstructor
    @NoArgsConstructor
    @Builder
    public static class Response{
        private String userName;
        private LocalDateTime reserveTime;
    }
}
