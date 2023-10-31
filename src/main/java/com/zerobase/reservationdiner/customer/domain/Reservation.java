package com.zerobase.reservationdiner.customer.domain;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.Id;
import jakarta.persistence.criteria.CriteriaBuilder;
import lombok.*;

import java.time.LocalDateTime;

@Getter
@Setter
@Builder
@AllArgsConstructor
@NoArgsConstructor
@Entity
@ToString(callSuper = true)
public class Reservation {

    @Id
    @GeneratedValue
    private Long id;

    private Long memberId;

    private Long storeId;

    private LocalDateTime reservationTime;

    private Integer customerCnt;

    private String phoneNumber;
}
