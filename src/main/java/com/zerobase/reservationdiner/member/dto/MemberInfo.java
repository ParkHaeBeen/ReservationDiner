package com.zerobase.reservationdiner.member.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

public class MemberInfo {

    @Getter @Setter @AllArgsConstructor @NoArgsConstructor
    public static class Request{
        private String memberId;
        private String memberPassword;
    }
    @Getter
    @Setter
    @AllArgsConstructor
    @NoArgsConstructor
    public static class Response{
        private String memberName;
    }

}
