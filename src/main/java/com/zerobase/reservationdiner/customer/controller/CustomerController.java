package com.zerobase.reservationdiner.customer.controller;

import com.zerobase.reservationdiner.customer.dto.CustomerSite;
import com.zerobase.reservationdiner.customer.dto.reservation.ReservationInfo;
import com.zerobase.reservationdiner.customer.dto.review.ReviewRole;
import com.zerobase.reservationdiner.customer.dto.review.ReviewWrite;
import com.zerobase.reservationdiner.customer.dto.store.StoreDetailInfo;
import com.zerobase.reservationdiner.customer.dto.store.StoreInfo;

import com.zerobase.reservationdiner.customer.exception.store.StoreException;
import com.zerobase.reservationdiner.customer.service.reservation.ReservationService;
import com.zerobase.reservationdiner.customer.service.review.ReviewService;
import com.zerobase.reservationdiner.customer.service.store.StoreService;
import com.zerobase.reservationdiner.customer.type.StoreErrorCode;
import com.zerobase.reservationdiner.owner.exception.OwnerException;
import com.zerobase.reservationdiner.owner.type.OwnerErrorCode;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequiredArgsConstructor
@Slf4j
public class CustomerController {

    private final StoreService storeService;
    private final ReservationService reservationService;
    private final ReviewService reviewService;

    @GetMapping("/store")
    public List<StoreInfo> getAllStore(@Valid @RequestBody CustomerSite site, BindingResult result){
        checkBindResultErrors(result);

        return storeService.getAllStore(site.getLatitude(), site.getLongtitude(),site.getStoreName());
    }

    @GetMapping("/store/detail/{storeId}")
    public StoreDetailInfo getStoreInfo(@PathVariable Long storeId){

        return storeService.getStore(storeId);
    }


    @PostMapping("/reservation")
    @PreAuthorize("hasRole('CUSTOMER')")
    public ReservationInfo.Response reservationStore(@Valid @RequestBody ReservationInfo.Request request
                                                                        ,BindingResult result){
        checkBindResultErrors(result);
        return reservationService.reservationStore(request);
    }

    @GetMapping("/review")
    @PreAuthorize("hasRole('CUSTOMER')")
    public ResponseEntity<?> checkPossibleWriteReview(@Valid @RequestBody ReviewRole reviewRole,BindingResult result){
        checkBindResultErrors(result);

        boolean checkReviewRole = reviewService.checkReviewRole(reviewRole);
        if(checkReviewRole){
            return ResponseEntity.ok("ok");
        }
        return ResponseEntity.badRequest().build();
    }

    @PostMapping("/review")
    @PreAuthorize("hasRole('CUSTOMER')")
    public ResponseEntity<?> insertReview(@Valid @RequestBody ReviewWrite reviewWrite,BindingResult result){
        checkBindResultErrors(result);
        reviewService.insertReview(reviewWrite);
        return ResponseEntity.ok("succeed");
    }


    private static void checkBindResultErrors(BindingResult result) {
        if(result.hasErrors()){
            throw new StoreException(StoreErrorCode.INVALID_STOREINFO);
        }
    }

}
