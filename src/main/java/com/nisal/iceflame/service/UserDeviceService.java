package com.nisal.iceflame.service;

import com.nisal.iceflame.model.UserDevice;
import com.nisal.iceflame.repository.UserDeviceRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class UserDeviceService {

    private final UserDeviceRepository userDeviceRepository;

    public String getUserFcmToken(Long userId) {   // <-- public
        return userDeviceRepository.findByUserId(userId)
                .map(UserDevice::getFcmToken)
                .orElse(null);
    }

    public void saveOrUpdateToken(Long userId, String token) {
        UserDevice device = userDeviceRepository.findByUserId(userId)
                .orElse(UserDevice.builder().userId(userId).build());
        device.setFcmToken(token);
        userDeviceRepository.save(device);
    }
}