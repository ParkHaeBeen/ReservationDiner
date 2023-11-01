package com.zerobase.reservationdiner.owner.domain;

import jakarta.persistence.Embeddable;
import lombok.*;

@Embeddable
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
@ToString
public class Address {

    private String city;
    private String street;
    private String zipcode;
    private Double lat;
    private Double lnt;
}
