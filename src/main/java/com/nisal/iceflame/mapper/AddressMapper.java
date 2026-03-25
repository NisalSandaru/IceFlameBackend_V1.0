package com.nisal.iceflame.mapper;

import com.nisal.iceflame.dto.AddressDto;
import com.nisal.iceflame.enums.AddressType;
import com.nisal.iceflame.model.Address;
import com.nisal.iceflame.model.User;

public class AddressMapper {

    public static Address toEntity(AddressDto dto, User user){
        return Address.builder()
                .id(dto.getId())
                .street(dto.getStreet())
                .city(dto.getCity())
                .postalCode(dto.getPostalCode())
//                .type(AddressType.valueOf(dto.getType()))
                .title(dto.getTitle())
                .isDefault(dto.getIsDefault())
                .user(user)
                .build();
    }

    public static AddressDto toDto(Address address){
        return AddressDto.builder()
                .id(address.getId())
                .street(address.getStreet())
                .city(address.getCity())
                .postalCode(address.getPostalCode())
//                .type(address.getType().getTitle())
                .title(address.getTitle())
                .isDefault(address.getIsDefault())
                .userId(address.getUser().getId())
                .build();
    }
}