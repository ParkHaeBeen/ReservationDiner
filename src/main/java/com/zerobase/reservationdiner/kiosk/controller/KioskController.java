package com.zerobase.reservationdiner.kiosk.controller;

import com.zerobase.reservationdiner.kiosk.dto.ArriveCheck;
import com.zerobase.reservationdiner.kiosk.dto.ArriveInfo;
import com.zerobase.reservationdiner.kiosk.exception.KioskException;
import com.zerobase.reservationdiner.kiosk.service.KioskService;
import com.zerobase.reservationdiner.kiosk.type.KioskErrorCode;
import com.zerobase.reservationdiner.member.exception.MemberException;
import com.zerobase.reservationdiner.member.type.MemberErrorCode;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
@RequestMapping("/kiosk")
@Slf4j
public class KioskController {
    private KioskService kioskService;

    @GetMapping("/arrive")
    public ArriveInfo.Response getReserveInfo(@Valid @RequestBody ArriveInfo.Request arriveInfo, BindingResult result) {
        checkBindResultErrors(result, KioskErrorCode.INVALID_KIOSK_INFO);
        return kioskService.getReserveInfo(arriveInfo);
    }

    @PostMapping("/arrive")
    public ResponseEntity<?> checkArrive(@Valid @RequestBody ArriveCheck arriveCheck, BindingResult result){
        checkBindResultErrors(result,KioskErrorCode.INVALID_KIOSK_INFO);

        return ResponseEntity.ok("success");
    }

    private static void checkBindResultErrors(BindingResult result, KioskErrorCode errorCode) {
        if (result.hasErrors()) {
            throw new KioskException(errorCode);
        }
    }
}
