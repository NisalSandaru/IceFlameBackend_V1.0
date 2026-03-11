package com.nisal.iceflame.dto;

import com.nisal.iceflame.enums.AddressType;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class AddressDto {

    private Long id;
    private String street;
    private String city;
    private String postalCode;
    private AddressType type;
    private Boolean isDefault;
    private Long userId;
}
