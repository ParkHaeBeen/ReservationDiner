package com.zerobase.reservationdiner.member.dto;

import com.zerobase.reservationdiner.member.domain.Member;
import com.zerobase.reservationdiner.member.type.MemberGrade;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;
import lombok.*;
import org.checkerframework.checker.units.qual.min;

import java.util.List;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class MemberInput {

    @Size(min = 5,max = 10)
    private String id;
    @Size(min = 5, max = 10)
    @NotNull
    private String password;
    @NotBlank
    private String name;
    @Pattern(regexp = "^01([0|1|6|7|8|9]?)-?([0-9]{3,4})-?([0-9]{4})$")
    private String phoneNumber;
    @NotNull
    private List<String> roles;

    public static Member from(MemberInput input){
        return Member.builder()
                .memberId(input.getId())
                .memberName(input.getName())
                .memberPassword(input.getPassword())
                .phoneNumber(input.getPhoneNumber())
                .roles(input.getRoles())
                .build();
    }

}
