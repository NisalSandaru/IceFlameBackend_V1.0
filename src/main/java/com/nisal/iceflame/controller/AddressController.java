package com.nisal.iceflame.controller;

import com.nisal.iceflame.dto.AddressDto;
import com.nisal.iceflame.service.AddressService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/addresses")
@RequiredArgsConstructor
public class AddressController {

    private final AddressService addressService;

    @PostMapping
    public AddressDto addAddress(@RequestBody AddressDto dto){
        return addressService.addAddress(dto);
    }

    @GetMapping("/user/{userId}")
    public List<AddressDto> getUserAddresses(@PathVariable Long userId){
        return addressService.getUserAddresses(userId);
    }

    @PutMapping("/{id}")
    public AddressDto updateAddress(
            @PathVariable Long id,
            @RequestBody AddressDto dto
    ){
        return addressService.updateAddress(id, dto);
    }

    @DeleteMapping("/{id}")
    public String deleteAddress(@PathVariable Long id){
        addressService.deleteAddress(id);
        return "Address deleted successfully";
    }

}
