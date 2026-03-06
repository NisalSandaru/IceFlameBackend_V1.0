package com.nisal.iceflame.mapper;

import com.nisal.iceflame.dto.CartDto;
import com.nisal.iceflame.dto.CartItemDto;
import com.nisal.iceflame.model.Cart;
import com.nisal.iceflame.model.CartItem;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@Component
@RequiredArgsConstructor
public class CartMapper {

    public static CartDto toDto(Cart cart){

        List<CartItemDto> items = cart.getItems()
                .stream()
                .map(CartMapper::toItemDto)
                .toList();

        return CartDto.builder()
                .id(cart.getId())
                .userId(cart.getUser().getId())
                .items(items)
                .createdAt(cart.getCreatedAt())
                .updatedAt(cart.getUpdatedAt())
                .build();
    }

    public static CartItemDto toItemDto(CartItem item){

        return CartItemDto.builder()
                .id(item.getId())
                .productId(item.getProduct().getId())
                .productName(item.getProduct().getName())
                .price(item.getProduct().getPrice())
                .quantity(item.getQuantity())
                .build();
    }

}
