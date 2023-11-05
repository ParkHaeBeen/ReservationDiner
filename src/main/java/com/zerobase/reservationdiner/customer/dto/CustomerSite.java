package com.zerobase.reservationdiner.customer.dto;

import jakarta.validation.constraints.NotBlank;
import lombok.*;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class CustomerSite {


    private double latitude;

    private double longtitude;
    private String storeName;
}
