package com.zerobase.reservationdiner.kiosk.dto;

import lombok.*;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class ArriveCheck {
    private Long timeSlotId;
    private Boolean check;
}
