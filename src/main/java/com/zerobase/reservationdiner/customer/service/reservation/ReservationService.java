package com.zerobase.reservationdiner.customer.service.reservation;

import com.zerobase.reservationdiner.customer.dto.reservation.ReservationInfo;

public interface ReservationService {
    ReservationInfo.Response reservationStore(ReservationInfo.Request request);
}
