package com.zerobase.reservationdiner.owner.dto;

import com.zerobase.reservationdiner.owner.domain.Address;
import com.zerobase.reservationdiner.owner.domain.OwnerStore;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.*;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class StoreInput {

        @NotBlank
        private String storeName;


        @NotBlank
        private String ownerId;

        @Size(min = 10)
        private String description;

        private Address address;

        public  static OwnerStore of(StoreInput input){
                return OwnerStore.builder()
                        .storeName(input.storeName)
                        .storeDescription(input.description)
                        .address(input.address)
                        .ownerId(input.getOwnerId())
                        .build();
        }

}
