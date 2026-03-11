package com.nisal.iceflame.mapper;

import com.nisal.iceflame.dto.AuthReq.RegisterRequest;
import com.nisal.iceflame.dto.UserDto;
import com.nisal.iceflame.model.User;

public class UserMapper {

    public static User toEntity(UserDto dto){
        return User.builder()
                .id(dto.getId())
                .fullName(dto.getFullName())
                .email(dto.getEmail())
                .mobileNumber(dto.getMobileNumber())
                .isActive(dto.getIsActive())
                .role(dto.getRole())
                .password(dto.getPassword())
                .profileImageUrl(dto.getProfileImageUrl())
                .createdAt(dto.getCreatedAt())
                .updatedAt(dto.getUpdatedAt())
                .build();
    }

    public static UserDto toDto(User entity){
        return UserDto.builder()
                .id(entity.getId())
                .fullName(entity.getFullName())
                .email(entity.getEmail())
                .mobileNumber(entity.getMobileNumber())
                .isActive(entity.getIsActive())
                .role(entity.getRole())
                .password(entity.getPassword())
                .profileImageUrl(entity.getProfileImageUrl())
                .createdAt(entity.getCreatedAt())
                .updatedAt(entity.getUpdatedAt())
                .build();
    }

}
