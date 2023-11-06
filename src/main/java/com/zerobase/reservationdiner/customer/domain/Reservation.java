package com.zerobase.reservationdiner.customer.domain;

import com.zerobase.reservationdiner.common.domain.BaseEntity;
import com.zerobase.reservationdiner.member.domain.Member;
import com.zerobase.reservationdiner.owner.domain.TimeSlot;
import jakarta.persistence.*;
import jakarta.persistence.criteria.CriteriaBuilder;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.*;
import org.springframework.data.annotation.CreatedDate;

import java.time.LocalDateTime;

@Getter
@Setter
@Builder
@AllArgsConstructor
@NoArgsConstructor
@Entity
@ToString(callSuper = true)
public class Reservation extends BaseEntity {

    @Id
    @GeneratedValue
    private Long id;

    @NotBlank
    @JoinColumn(name = "member_Id")
    @ManyToOne(fetch = FetchType.LAZY,cascade = CascadeType.PERSIST)
    private Member member;

    @NotNull
    private Integer customerCnt;

    @NotBlank
    private String phoneNumber;

    @NotNull
    @JoinColumn(name = "timesolt_Id")
    @OneToOne(fetch = FetchType.EAGER,cascade = CascadeType.ALL)
    private TimeSlot timeSlot;

    private Boolean ownercheck=false;

    private Boolean cancel=false;

    private Boolean visit=false;

}
