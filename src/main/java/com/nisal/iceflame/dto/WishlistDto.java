package com.nisal.iceflame.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;
import java.util.List;
@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class WishlistDto {
    private Long id;
    private Long userId;
    private List<WishlistItemDto> items;
    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;
}
