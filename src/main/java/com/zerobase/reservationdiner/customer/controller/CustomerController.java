package com.zerobase.reservationdiner.customer.controller;

import com.zerobase.reservationdiner.customer.dto.CustomerSite;
import com.zerobase.reservationdiner.customer.dto.StoreInfo;
import com.zerobase.reservationdiner.customer.repository.CustomerRepository;
import com.zerobase.reservationdiner.customer.service.CustomerService;
import com.zerobase.reservationdiner.owner.exception.OwnerException;
import com.zerobase.reservationdiner.owner.repository.OwnerRepository;
import com.zerobase.reservationdiner.owner.service.OwnerService;
import com.zerobase.reservationdiner.owner.type.OwnerErrorCode;
import jakarta.validation.Valid;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
@Slf4j
public class CustomerController {

    private final CustomerService customerService;

    @GetMapping("/store")
    public StoreInfo getAllStore(@RequestBody CustomerSite site){
        customerService.getAllStore(site.getLatitude(),site.getLongtitude());

    }

    private static void checkBindResultErrors(BindingResult result) {
        if(result.hasErrors()){
            throw new OwnerException(OwnerErrorCode.INVALID_STOREINFO);
        }
    }
}
