package com.zerobase.reservationdiner.customer.dto;

import com.zerobase.reservationdiner.owner.domain.Address;
import lombok.*;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class StoreInfo {
    private String storeName;
    private Address address;
    private Long storeId;
    private double star;
}
