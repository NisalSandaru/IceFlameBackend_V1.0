package com.nisal.iceflame.mapper;

import com.nisal.iceflame.dto.OrderDto;
import com.nisal.iceflame.dto.OrderItemDto;
import com.nisal.iceflame.model.Order;
import com.nisal.iceflame.model.OrderItem;

import java.util.List;

public class OrderMapper {

    public static OrderDto toDto(Order order){

        List<OrderItemDto> items = order.getItems()
                .stream()
                .map(OrderMapper::toItemDto)
                .toList();

        return OrderDto.builder()
                .id(order.getId())
                .userId(order.getUserId())
                .addressId(order.getAddressId())
                .totalAmount(order.getTotalAmount())
                .status(order.getStatus())
                .paymentStatus(order.getPaymentStatus())
                .paymentMethod(order.getPaymentMethod())
                .createdAt(order.getCreatedAt())
                .items(items)
                .build();
    }

    private static OrderItemDto toItemDto(OrderItem item){

        return OrderItemDto.builder()
                .id(item.getId())
                .productId(item.getProduct().getId())
                .productName(item.getProduct().getName())
                .imgUrl(item.getProduct().getImages().get(0).getImageUrl())
                .quantity(item.getQuantity())
                .price(item.getPrice())
                .build();
    }

}
