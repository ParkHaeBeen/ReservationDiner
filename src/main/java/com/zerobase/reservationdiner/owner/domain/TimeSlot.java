package com.zerobase.reservationdiner.owner.domain;

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
    @JoinColumn(name = "ownerstore_id")
    private OwnerStore store;

    @NotNull
    private LocalDateTime time;


    private Boolean reserve=false;


}
