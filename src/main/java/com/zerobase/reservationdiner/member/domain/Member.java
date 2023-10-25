package com.zerobase.reservationdiner.member.domain;

import com.zerobase.reservationdiner.member.type.MemberGrade;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;
import lombok.*;

@Getter
@Setter
@Builder
@AllArgsConstructor
@NoArgsConstructor
@Entity
@ToString(callSuper = true)
public class Member extends BaseEntity {

    @Id
    @GeneratedValue
    private Long id;

    @Size(min = 5,max = 10)
    private String memberId;
    @Size(min = 5,max = 10)
    private String memberPassword;

    @NotBlank
    private String memberName;

    @Pattern(regexp = "^01([0|1|6|7|8|9]?)-?([0-9]{3,4})-?([0-9]{4})$")
    private String phoneNumber;

    @Enumerated(EnumType.STRING)
    @NotNull
    private MemberGrade memberGrade;
}
