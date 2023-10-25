package com.zerobase.reservationdiner.member.dto;

import lombok.*;

import java.util.List;

public class MemberInfo {

    @Getter
    @Setter
    @AllArgsConstructor
    @NoArgsConstructor
    @Builder
    public static class Request{
        private String memberId;
        private String memberPassword;
    }

    @Getter
    @Setter
    @AllArgsConstructor
    @NoArgsConstructor
    @Builder
    public static class Response{
        private String memberName;
        private List<String> roles;
    }

}
