package com.zerobase.reservationdiner.owner.dto;

import com.zerobase.reservationdiner.owner.domain.OwnerStore;
import lombok.*;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
@ToString
public class StoreRegister {
    private Long storeId;
    private String storeName;

    static public StoreRegister of(OwnerStore ownerStore){
        return StoreRegister.builder()
                .storeId(ownerStore.getId())
                .storeName(ownerStore.getStoreName())
                .build();
    }
}
