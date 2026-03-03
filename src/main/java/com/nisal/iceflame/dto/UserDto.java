package com.nisal.iceflame.dto;

import com.nisal.iceflame.enums.Role;
import com.nisal.iceflame.model.Address;
import jakarta.persistence.Column;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

import java.time.LocalDateTime;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class UserDto {
    private Long id;
    private String fullName;
    private String email;
    private String password;
    private String profileImageUrl;
    private Address address;
    private Role role;
    private Boolean isActive;

    private LocalDateTime createdAt;

    private LocalDateTime updatedAt;
}
