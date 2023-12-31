package com.zerobase.reservationdiner.member.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;
import lombok.*;

import java.util.List;

public class MemberInfo {

    @Getter
    @Setter
    @AllArgsConstructor
    @NoArgsConstructor
    @Builder
    public static class Request{

        @Size(min = 5,max = 10)
        @NotNull
        private String memberId;

        @Size(min = 5,max = 10)
        @NotNull
        private String memberPassword;

    }

    @Getter
    @Setter
    @AllArgsConstructor
    @NoArgsConstructor
    @Builder
    public static class Response{
        private String memberName;
        private String memberId;
        private List<String> roles;
    }

}
