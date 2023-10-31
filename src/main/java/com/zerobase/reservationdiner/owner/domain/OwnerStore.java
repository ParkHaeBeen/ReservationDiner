package com.zerobase.reservationdiner.owner.domain;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.Id;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.*;

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

    @NotBlank
    private String city;

    @NotBlank
    private String street;

    @NotBlank
    private String zipcode;

    @Size(min = 10)
    private String storeDescription;
}
