package com.nisal.iceflame.dto.AuthReq;

import lombok.Data;

@Data
public class SignInRequest {
    private String email;
    private String password;
}
