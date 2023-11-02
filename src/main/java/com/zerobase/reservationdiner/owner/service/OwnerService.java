package com.zerobase.reservationdiner.owner.service;

import com.zerobase.reservationdiner.owner.dto.ReserveInfo;
import com.zerobase.reservationdiner.owner.dto.StoreInput;
import com.zerobase.reservationdiner.owner.dto.StoreOpen;

public interface OwnerService {

    void registerStore(StoreInput input);

    void openAndTimeSlotAdd(StoreOpen open);

    void permitReservation(ReserveInfo.Request request);
}
