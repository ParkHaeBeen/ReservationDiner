package com.zerobase.reservationdiner.owner.domain;

import com.zerobase.reservationdiner.customer.domain.Reservation;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;
import lombok.*;

import java.time.LocalDateTime;

@Getter
@Setter
@Builder
@AllArgsConstructor
@NoArgsConstructor
@Entity
public class TimeSlot {
    @Id
    @GeneratedValue
    private Long id;

    @ManyToOne
    @JoinColumn(name = "ownerstore_Id")
    private OwnerStore store;

    @OneToOne(fetch = FetchType.EAGER,mappedBy = "timeSlot")
    private Reservation reservation;

    @NotNull
    private LocalDateTime time;


    private Boolean reserve=false;
}
