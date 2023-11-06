package com.zerobase.reservationdiner.customer.domain;

import com.zerobase.reservationdiner.common.domain.BaseEntity;
import com.zerobase.reservationdiner.member.domain.Member;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;
import lombok.*;

@Getter
@Setter
@Builder
@AllArgsConstructor
@NoArgsConstructor
@Entity
@ToString(callSuper = true)
public class Review extends BaseEntity {

    @Id @GeneratedValue
    private Long Id;

    @JoinColumn(name = "member_Id")
    @ManyToOne(fetch = FetchType.EAGER)
    private Member member;

    @JoinColumn(name = "reservation_ID")
    @OneToOne(fetch = FetchType.LAZY)
    private Reservation reservation;

    @NotNull
    private String reviewName;

    @NotNull
    private String reviewDescription;
}
