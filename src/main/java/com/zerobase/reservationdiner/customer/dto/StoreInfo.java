package com.zerobase.reservationdiner.customer.dto;

import lombok.*;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class StoreInfo {
    private String storeName;
    private String zipcode;
    private double star;
}
