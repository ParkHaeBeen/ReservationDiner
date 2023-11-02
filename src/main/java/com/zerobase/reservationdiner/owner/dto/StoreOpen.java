package com.zerobase.reservationdiner.owner.dto;

import lombok.*;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class StoreOpen {
    private String ownerId;
    private Long storeId;
    private Boolean open;
}
