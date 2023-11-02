package com.zerobase.reservationdiner.customer.domain;

import com.zerobase.reservationdiner.common.domain.BaseEntity;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.Id;
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
    private String memberId;

    @NotNull
    private Long storeId;

    @NotNull
    private LocalDateTime reservationDate;

    @NotNull
    private Integer customerCnt;

    @NotBlank
    private String phoneNumber;

    @NotNull
    private Long timeslotId;

    private Boolean ownercheck=false;

    private Boolean cancel=false;

}
