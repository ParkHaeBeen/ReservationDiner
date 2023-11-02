package com.zerobase.reservationdiner.owner.dto;

import com.zerobase.reservationdiner.common.dto.AddressDto;
import com.zerobase.reservationdiner.owner.domain.OwnerStore;
import jakarta.persistence.Convert;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.*;
import org.springframework.data.jpa.convert.threeten.Jsr310JpaConverters;

import java.time.LocalTime;

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

        @NotNull
        private AddressDto address;

        @NotNull
        private LocalTime openTime;

        @NotNull
        private LocalTime closeTime;
        public  static OwnerStore of(StoreInput input){
                return OwnerStore.builder()
                        .storeName(input.storeName)
                        .storeDescription(input.description)
                        .address(AddressDto.of(input.address))
                        .ownerId(input.getOwnerId())
                        .openTime(input.openTime)
                        .closeTime(input.closeTime)
                        .build();
        }

}
