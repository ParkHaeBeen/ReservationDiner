package com.zerobase.reservationdiner.kiosk.service;

import com.zerobase.reservationdiner.kiosk.dto.ArriveCheck;
import com.zerobase.reservationdiner.kiosk.dto.ArriveInfo;

public interface KioskService {

    ArriveInfo.Response getReserveInfo(ArriveInfo.Request info);

    void getCheckVisit(ArriveCheck check);


}
