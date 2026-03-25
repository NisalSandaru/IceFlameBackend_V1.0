package com.nisal.iceflame.service;

import com.nisal.iceflame.dto.AddressDto;
import com.nisal.iceflame.exceptions.AddressException;
import com.nisal.iceflame.exceptions.UserException;
import com.nisal.iceflame.mapper.AddressMapper;
import com.nisal.iceflame.model.Address;
import com.nisal.iceflame.model.User;
import com.nisal.iceflame.repository.AddressRepository;
import com.nisal.iceflame.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class AddressService {

    private final AddressRepository addressRepository;
    private final UserRepository userRepository;

    public AddressDto addAddress(AddressDto dto){

        User user = userRepository.findById(dto.getUserId())
                .orElseThrow(() -> new UserException("User not found", HttpStatus.NOT_FOUND));

        if(Boolean.TRUE.equals(dto.getIsDefault())){
            addressRepository.findByUserIdAndIsDefaultTrue(user.getId())
                    .ifPresent(addr -> {
                        addr.setIsDefault(false);
                        addressRepository.save(addr);
                    });
        }

        Address address = AddressMapper.toEntity(dto, user);

        return AddressMapper.toDto(addressRepository.save(address));
    }

    public List<AddressDto> getUserAddresses(Long userId){

        return addressRepository.findByUserId(userId)
                .stream()
                .map(AddressMapper::toDto)
                .toList();
    }

    public AddressDto updateAddress(Long id, AddressDto dto){

        Address address = addressRepository.findById(id)
                .orElseThrow(() -> new AddressException("Address not found", HttpStatus.NOT_FOUND));

        address.setStreet(dto.getStreet());
        address.setCity(dto.getCity());
        address.setPostalCode(dto.getPostalCode());
        address.setTitle(dto.getTitle());

        return AddressMapper.toDto(addressRepository.save(address));
    }

    public void deleteAddress(Long id){

        Address address = addressRepository.findById(id)
                .orElseThrow(() -> new AddressException("Address not found", HttpStatus.NOT_FOUND));

        addressRepository.delete(address);
    }

}
