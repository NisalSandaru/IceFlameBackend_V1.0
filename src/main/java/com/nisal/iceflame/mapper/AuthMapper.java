package com.nisal.iceflame.mapper;

import com.nisal.iceflame.dto.AuthReq.RegisterRequest;
import com.nisal.iceflame.dto.AuthReq.SignInRequest;
import com.nisal.iceflame.model.User;

public class AuthMapper {
    public static User toRegEntity(RegisterRequest dto){
        return User.builder()
                .fullName(dto.getFullName())
                .email(dto.getEmail())
                .password(dto.getPassword())
                .build();
    }

    public static RegisterRequest toRegDto(User user){
        return RegisterRequest.builder()
                .fullName(user.getFullName())
                .email(user.getEmail())
                .build();
    }

    public static User toSignInEntity(SignInRequest dto){
        return User.builder()
                .email(dto.getEmail())
                .password(dto.getPassword())
                .build();
    }

}
