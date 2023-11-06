package com.zerobase.reservationdiner.owner.service;

import com.zerobase.reservationdiner.owner.dto.*;

import java.util.List;

public interface OwnerService {

    StoreRegister registerStore(StoreInput input);

    void openAndTimeSlotAdd(StoreOpen open);

    boolean permitReservation(ReserveInfo.Request request);

    List<ReserveList.Response> getAllReservation(ReserveList.Request request);
}
