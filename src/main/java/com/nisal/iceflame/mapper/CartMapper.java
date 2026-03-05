package com.nisal.iceflame.mapper;

import com.nisal.iceflame.dto.CartDto;
import com.nisal.iceflame.model.Cart;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@Component
@RequiredArgsConstructor
public class CartMapper {

    private final CartItemMapper cartItemMapper;

    public static CartDto toDto(Cart cart) {

        if (cart == null) return null;

        return CartDto.builder()
                .id(cart.getId())
                .user(cart.getUser())
                .createdAt(cart.getCreatedAt())
                .updatedAt(cart.getUpdatedAt())
                .items(
                        cart.getItems() != null
                                ? cart.getItems()
                                .stream()
                                .map(CartItemMapper::toDto)
                                .collect(Collectors.toList())
                                : new ArrayList<>()
                )
                .build();
    }

    public static Cart toEntity(CartDto dto) {

        if (dto == null) return null;

        return Cart.builder()
                .id(dto.getId())
                .user(dto.getUser())
                .createdAt(dto.getCreatedAt())
                .updatedAt(dto.getUpdatedAt())
                .build();
    }
}
