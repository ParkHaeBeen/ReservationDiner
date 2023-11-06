package com.zerobase.reservationdiner.owner.controller;

import com.zerobase.reservationdiner.owner.dto.*;
import com.zerobase.reservationdiner.owner.exception.OwnerException;
import com.zerobase.reservationdiner.owner.service.OwnerService;
import com.zerobase.reservationdiner.owner.type.OwnerErrorCode;
import jakarta.validation.Valid;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.HttpStatusCode;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import java.util.List;

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
        StoreRegister storeRegister = ownerService.registerStore(store);
        return ResponseEntity.ok(storeRegister.getStoreName());
    }

    @PostMapping("/openstatus")
    @PreAuthorize("hasRole('OWNER')")
    public ResponseEntity<?> realOpen(@Valid @RequestBody StoreOpen open,BindingResult result){
        checkBindResultErrors(result);
        ownerService.openAndTimeSlotAdd(open);
        return ResponseEntity.ok("succeed");
    }

    @PostMapping("/permitreserve")
    @PreAuthorize("hasRole('OWNER')")
    public ResponseEntity<?> permitReserve(@Valid @RequestBody ReserveInfo.Request request,BindingResult result){
        checkBindResultErrors(result);

        boolean permit = ownerService.permitReservation(request);

        return ResponseEntity.ok(permit);
    }

    @GetMapping("/reservations")
    @PreAuthorize("hasRole('OWNER')")
    public List<ReserveList.Response> showAllReserve(@Valid @RequestBody ReserveList.Request request, BindingResult result){
        checkBindResultErrors(result);

        return ownerService.getAllReservation(request);
    }

    private static void checkBindResultErrors(BindingResult result) {
        if(result.hasErrors()){
            throw new OwnerException(OwnerErrorCode.INVALID_STOREINFO);
        }
    }

}
