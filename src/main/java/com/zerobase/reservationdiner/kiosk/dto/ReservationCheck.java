package com.zerobase.reservationdiner.kiosk.dto;

import lombok.*;

import java.time.LocalDateTime;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class ReservationCheck {
    private LocalDateTime time;
    private Integer customerCnt;
    private Long timeSlotId;
    private Boolean visit;

}
