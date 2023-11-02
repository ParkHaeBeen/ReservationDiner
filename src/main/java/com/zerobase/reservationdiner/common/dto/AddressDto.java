package com.zerobase.reservationdiner.common.dto;

import com.zerobase.reservationdiner.owner.domain.Address;
import lombok.*;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class AddressDto {

    private String city;
    private String street;
    private String zipcode;
    private Double lat;
    private Double lnt;

    public static Address of(AddressDto address){
        return Address.builder()
                .city(address.getCity())
                .street(address.getStreet())
                .zipcode(address.getZipcode())
                .lat(address.getLat())
                .lnt(address.getLnt())
                .build();
    }

    public static AddressDto ofAddress(Address address){
        return AddressDto.builder()
                .city(address.getCity())
                .street(address.getStreet())
                .zipcode(address.getZipcode())
                .lat(address.getLat())
                .lnt(address.getLnt())
                .build();
    }
}
