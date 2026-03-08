package com.nisal.iceflame.mapper;

import com.nisal.iceflame.dto.CartDto;
import com.nisal.iceflame.dto.CartItemDto;
import com.nisal.iceflame.dto.WishlistDto;
import com.nisal.iceflame.dto.WishlistItemDto;
import com.nisal.iceflame.model.Cart;
import com.nisal.iceflame.model.CartItem;
import com.nisal.iceflame.model.Wishlist;
import com.nisal.iceflame.model.WishlistItem;

import java.util.List;

public class WishlistMapper {

    public static WishlistDto toDto(Wishlist list){

        List<WishlistItemDto> items = list.getItems()
                .stream()
                .map(WishlistMapper::toItemDto)
                .toList();

        return WishlistDto.builder()
                .id(list.getId())
                .userId(list.getUser().getId())
                .items(items)
                .createdAt(list.getCreatedAt())
                .updatedAt(list.getUpdatedAt())
                .build();
    }

    public static WishlistItemDto toItemDto(WishlistItem item){

        return WishlistItemDto.builder()
                .id(item.getId())
                .productId(item.getProduct().getId())
                .productName(item.getProduct().getName())
                .imageUrl(item.getProduct().getImages().get(0).getImageUrl())
                .rating(item.getProduct().getRating())
                .price(item.getProduct().getPrice())
                .build();
    }

}
