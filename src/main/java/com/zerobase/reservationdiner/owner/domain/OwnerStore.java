package com.zerobase.reservationdiner.owner.domain;

import jakarta.persistence.*;
import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.*;
import org.hibernate.annotations.ColumnDefault;

@Getter
@Setter
@Builder
@AllArgsConstructor
@NoArgsConstructor
@Entity
@ToString(callSuper = true)
public class OwnerStore {

    @Id
    @GeneratedValue
    private Long id;

    @NotBlank
    private String ownerId;

    @NotBlank
    private String storeName;

    @Enumerated(EnumType.STRING)
    @Embedded
    private Address address;

    @Size(min = 10)
    private String storeDescription;

    @ColumnDefault("0")
    @Max(5)
    private Double star;
}
