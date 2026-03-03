package com.nisal.iceflame.dto;

import com.nisal.iceflame.model.ProductImage;
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
public class ProductDto {
    private Long id;
    private String name;
    private String description;
    private Double price;
    private List<String> images;
    private boolean isActive;
    private Long categoryId;
    private Double rating;
    private String portionSize;
    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;
}
