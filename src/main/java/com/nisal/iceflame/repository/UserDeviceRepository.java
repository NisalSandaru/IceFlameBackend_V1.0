package com.nisal.iceflame.repository;

import com.nisal.iceflame.model.UserDevice;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface UserDeviceRepository extends JpaRepository<UserDevice, Long> {
    Optional<UserDevice> findByUserId(Long userId);
}
