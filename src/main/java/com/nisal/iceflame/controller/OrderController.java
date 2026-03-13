package com.nisal.iceflame.controller;

import com.nisal.iceflame.dto.CheckoutRequest;
import com.nisal.iceflame.dto.OrderDto;
import com.nisal.iceflame.model.Order;
import com.nisal.iceflame.service.OrderService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/orders")
@RequiredArgsConstructor
public class OrderController {

    private final OrderService orderService;

    @PostMapping("/checkout")
    public ResponseEntity<OrderDto> checkout(
            @RequestBody CheckoutRequest request
    ) {

        OrderDto order = orderService.checkout(
                request.getUserId(),
                request.getAddressId(),
                request.getPaymentMethod()
        );

        return ResponseEntity.ok(order);
    }
}
