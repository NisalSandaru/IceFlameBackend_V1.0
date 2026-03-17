package com.nisal.iceflame.controller;

import com.nisal.iceflame.service.UserDeviceService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/device")
@RequiredArgsConstructor
public class UserDeviceController {

    private final UserDeviceService userDeviceService;

    @PostMapping
    public void saveToken(
            @RequestParam Long userId,
            @RequestParam String token
    ) {
        userDeviceService.saveOrUpdateToken(userId, token);
    }
}