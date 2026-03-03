package com.nisal.iceflame.mapper;

import com.nisal.iceflame.dto.CategoryDto;
import com.nisal.iceflame.model.Category;

public class CategoryMapper {

    public static Category toEntity(CategoryDto dto){
        return Category.builder()
                .id(dto.getId())
                .name(dto.getName())
                .description(dto.getDescription())
                .isActive(dto.getIsActive())
                .catImageUrl(dto.getCatImageUrl())
                .createdAt(dto.getCreatedAt())
                .updatedAt(dto.getUpdatedAt())
                .build();
    }

    public static CategoryDto toDto(Category entity){
        return CategoryDto.builder()
                .id(entity.getId())
                .name(entity.getName())
                .description(entity.getDescription())
                .isActive(entity.getIsActive())
                .catImageUrl(entity.getCatImageUrl())
                .createdAt(entity.getCreatedAt())
                .updatedAt(entity.getUpdatedAt())
                .build();
    }
}
