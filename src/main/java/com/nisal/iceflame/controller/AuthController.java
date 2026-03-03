package com.nisal.iceflame.controller;

import com.nisal.iceflame.dto.AuthReq.RegisterRequest;
import com.nisal.iceflame.dto.AuthReq.SignInRequest;
import com.nisal.iceflame.dto.UserDto;
import com.nisal.iceflame.exceptions.UserException;
import com.nisal.iceflame.service.AuthService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/auth")
@RequiredArgsConstructor
public class AuthController {

    private final AuthService authService;

    @PostMapping("/signup")
    public ResponseEntity<RegisterRequest> signUpHandler(
            @RequestBody @Valid RegisterRequest dto
    ) throws UserException {
        return ResponseEntity.status(HttpStatus.CREATED).body(authService.regUser(dto));
    }

    @PostMapping("/signin")
    public ResponseEntity<UserDto> signInHandler(
            @RequestBody SignInRequest dto
            ) throws UserException {
        return ResponseEntity.ok(authService.signIn(dto));
    }

    @PostMapping("/admin/signup")
    public ResponseEntity<RegisterRequest> signUpHandlerAdmin(
            @RequestBody @Valid RegisterRequest dto
    ) throws UserException {
        return ResponseEntity.status(HttpStatus.CREATED).body(authService.regAdmin(dto));
    }

    @PostMapping("/admin/signin")
    public ResponseEntity<UserDto> signInHandlerAdmin(
            @RequestBody SignInRequest dto
    ) throws UserException {
        return ResponseEntity.ok(authService.signInAdmin(dto));
    }
}
