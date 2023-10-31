package com.zerobase.reservationdiner.owner.dto;

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
        private String city;

        @NotBlank
        private String ownerId;

        @NotBlank
        private String street;

        @NotBlank
        private String zipcode;

        @Size(min = 10)
        private String description;

        public  static OwnerStore of(StoreInput input){
                return OwnerStore.builder()
                        .city(input.city)
                        .storeName(input.storeName)
                        .storeDescription(input.description)
                        .street(input.street)
                        .zipcode(input.zipcode)
                        .ownerId(input.getOwnerId())
                        .build();
        }

}
