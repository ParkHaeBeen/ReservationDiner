package com.zerobase.reservationdiner.customer.dto.store;

import com.zerobase.reservationdiner.common.dto.AddressDto;
import com.zerobase.reservationdiner.owner.domain.OwnerStore;
import lombok.*;

import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.List;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class StoreDetailInfo {
    private Long storeId;
    private String storeName;
    private String description;
    private AddressDto address;
    private double star;
    private LocalTime openTime;
    private LocalTime closeTime;
    private List<LocalDateTime> timeSlots;

    public static StoreDetailInfo of(OwnerStore store) {
        return StoreDetailInfo.builder()
                .storeId(store.getId())
                .storeName(store.getStoreName())
                .description(store.getStoreDescription())
                .address(AddressDto.ofAddress(store.getAddress()))
                .star(store.getStar())
                .openTime(store.getOpenTime())
                .closeTime(store.getCloseTime())
                .build();
    }
}
