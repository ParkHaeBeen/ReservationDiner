package com.zerobase.reservationdiner.owner.controller;

import com.zerobase.reservationdiner.owner.dto.ReserveInfo;
import com.zerobase.reservationdiner.owner.dto.StoreInput;
import com.zerobase.reservationdiner.owner.dto.StoreOpen;
import com.zerobase.reservationdiner.owner.exception.OwnerException;
import com.zerobase.reservationdiner.owner.service.OwnerService;
import com.zerobase.reservationdiner.owner.type.OwnerErrorCode;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.HttpStatusCode;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/owner")
@RequiredArgsConstructor
@Slf4j
public class OwnerController {

    private final OwnerService ownerService;

    @PostMapping("/newstore")
    @PreAuthorize("hasRole('OWNER')")
    public ResponseEntity<String>  registerStore(@Valid @RequestBody StoreInput store, BindingResult result){
        checkBindResultErrors(result);

        ownerService.registerStore(store);

        return ResponseEntity.ok(store.getStoreName());
    }

    @PostMapping("/openstatus")
    @PreAuthorize("hasRole('OWNER')")
    public ResponseEntity<?> realOpen(@Valid @PathVariable StoreOpen open,BindingResult result){
        checkBindResultErrors(result);
        ownerService.openAndTimeSlotAdd(open);
        return ResponseEntity.ok("succeed");
    }

    @PostMapping("/permitreserve")
    @PreAuthorize("hasRole('OWNER')")
    public ResponseEntity<?> permitReserve(@Valid @RequestBody ReserveInfo.Request request,BindingResult result){
        checkBindResultErrors(result);

        ownerService.permitReservation(request);

        return ResponseEntity.ok("succeed");
    }

    private static void checkBindResultErrors(BindingResult result) {
        if(result.hasErrors()){
            throw new OwnerException(OwnerErrorCode.INVALID_STOREINFO);
        }
    }

}
