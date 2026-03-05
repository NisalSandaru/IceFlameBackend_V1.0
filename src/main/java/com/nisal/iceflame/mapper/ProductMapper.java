package com.nisal.iceflame.mapper;

import com.nisal.iceflame.dto.ProductDto;
import com.nisal.iceflame.model.Product;
import com.nisal.iceflame.model.ProductImage;

import java.util.List;
import java.util.stream.Collectors;

public class ProductMapper {

    // Entity -> DTO
    public static ProductDto toDto(Product product) {
        if (product == null) return null;

        List<String> imageUrls = product.getImages() != null ?
                product.getImages().stream()
                        .map(ProductImage::getImageUrl)
                        .collect(Collectors.toList())
                : List.of();

        return ProductDto.builder()
                .id(product.getId())
                .name(product.getName())
                .description(product.getDescription())
                .price(product.getPrice())
                .images(imageUrls)
                .isActive(product.getIsActive())
                .categoryId(product.getCategory() != null ? product.getCategory().getId() : null)
                .rating(product.getRating())
                .portionSize(product.getPortionSize())
                .kcal(product.getKcal())
                .createdAt(product.getCreatedAt())
                .updatedAt(product.getUpdatedAt())
                .build();
    }

    // DTO -> Entity
    public static Product toEntity(ProductDto dto) {
        if (dto == null) return null;

        Product product = new Product();
        product.setId(dto.getId());
        product.setName(dto.getName());
        product.setDescription(dto.getDescription());
        product.setPrice(dto.getPrice());
        product.setIsActive(dto.getIsActive());
        product.setPortionSize(dto.getPortionSize());
        product.setRating(dto.getRating());
        product.setKcal(dto.getKcal());

        // Convert URLs to ProductImage entities
        if (dto.getImages() != null && !dto.getImages().isEmpty()) {
            List<ProductImage> images = dto.getImages().stream()
                    .map(url -> ProductImage.builder().imageUrl(url).product(product).build())
                    .collect(Collectors.toList());
            product.setImages(images);
        }

        return product;
    }
}
