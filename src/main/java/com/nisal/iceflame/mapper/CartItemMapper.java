package com.nisal.iceflame.mapper;

import com.nisal.iceflame.dto.CartItemDto;
import com.nisal.iceflame.model.CartItem;
import org.springframework.stereotype.Component;

@Component
public class CartItemMapper {

    public static CartItemDto toDto(CartItem item) {

        if (item == null) return null;

        return CartItemDto.builder()
                .id(item.getId())
                .cart(item.getCart())
                .product(item.getProduct())
                .quantity(item.getQuantity())
                .build();
    }

    public static CartItem toEntity(CartItemDto dto) {

        if (dto == null) return null;

        return CartItem.builder()
                .id(dto.getId())
                .cart(dto.getCart())
                .product(dto.getProduct())
                .quantity(dto.getQuantity())
                .build();
    }
}
