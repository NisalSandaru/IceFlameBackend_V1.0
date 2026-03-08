package com.nisal.iceflame.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class WishlistItemDto {
    private Long id;
    private Long productId;
    private String productName;
    private String imageUrl;
    private Double rating;
    private Double price;
}
