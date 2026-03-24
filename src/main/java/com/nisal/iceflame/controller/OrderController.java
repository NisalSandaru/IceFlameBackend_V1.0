package com.nisal.iceflame.controller;

import com.nisal.iceflame.dto.CheckoutRequest;
import com.nisal.iceflame.dto.OrderDto;
import com.nisal.iceflame.model.Order;
import com.nisal.iceflame.service.OrderService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/orders")
@RequiredArgsConstructor
public class OrderController {

    private final OrderService orderService;

    @PostMapping("/create")
    public ResponseEntity<OrderDto> createOrder(
            @RequestBody CheckoutRequest request
    ) {

        OrderDto order = orderService.createOrder(
                request.getUserId(),
                request.getAddressId(),
                request.getPaymentMethod()
        );

        return ResponseEntity.ok(order);
    }

    @PostMapping("/confirm-payment/{orderId}")
    public ResponseEntity<OrderDto> confirmPayment(@PathVariable Long orderId) {

        return ResponseEntity.ok(orderService.confirmPayment(orderId));
    }

    // ALL ORDERS
    @GetMapping("/user/{userId}")
    public List<OrderDto> getOrdersByUser(@PathVariable Long userId) {

        return orderService.getOrdersByUser(userId);
    }

    @GetMapping("/user/{userId}/current")
    public List<OrderDto> getCurrentOrders(@PathVariable Long userId) {
        return orderService.getCurrentOrders(userId);
    }

    @GetMapping("/user/{userId}/previous")
    public List<OrderDto> getPreviousOrders(@PathVariable Long userId) {
        return orderService.getPreviousOrders(userId);
    }

    @GetMapping("getById/{orderId}")
    public OrderDto getById(@PathVariable Long orderId) {
        return orderService.getByOrderId(orderId);
    }

    // GET ALL ORDERS (ADMIN)
    @GetMapping
    public ResponseEntity<List<OrderDto>> getAllOrders() {
        return ResponseEntity.ok(orderService.getAllOrders());
    }

    // UPDATE STATUS (ADMIN)
    @PutMapping("/status/{orderId}")
    public ResponseEntity<OrderDto> updateStatus(
            @PathVariable Long orderId,
            @RequestParam String status
    ) {
        return ResponseEntity.ok(orderService.updateStatus(orderId, status));
    }
}
