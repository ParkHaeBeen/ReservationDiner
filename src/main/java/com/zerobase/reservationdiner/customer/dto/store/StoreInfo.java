package com.zerobase.reservationdiner.customer.dto.store;

import com.zerobase.reservationdiner.common.dto.AddressDto;
import com.zerobase.reservationdiner.owner.domain.Address;
import lombok.*;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class StoreInfo {
    private String storeName;
    private AddressDto address;
    private Long storeId;
    private double star;
}
