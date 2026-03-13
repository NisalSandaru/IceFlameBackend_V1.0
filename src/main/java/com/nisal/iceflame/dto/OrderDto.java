package com.nisal.iceflame.dto;

import com.nisal.iceflame.enums.OrderStatus;
import com.nisal.iceflame.enums.PaymentMethod;
import com.nisal.iceflame.enums.PaymentStatus;
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
public class OrderDto {

    private Long id;

    private Long userId;

    private Long addressId;

    private Double totalAmount;

    private OrderStatus status;

    private PaymentStatus paymentStatus;
    private PaymentMethod paymentMethod;

    private LocalDateTime createdAt;

    private List<OrderItemDto> items;

}
