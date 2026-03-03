package com.nisal.iceflame.dto.AuthReq;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class RegisterRequest {
    @NotBlank(message = "fullName is required")
    public String fullName;
    @NotBlank(message = "Email is required")
    @Email(message = "Please provide a valid email")
    public String email;
    @NotBlank(message = "Password is required")
    public String password;
}
