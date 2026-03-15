package com.nisal.iceflame.service;

import com.nisal.iceflame.dto.CartDto;
import com.nisal.iceflame.dto.OrderDto;
import com.nisal.iceflame.enums.OrderStatus;
import com.nisal.iceflame.enums.PaymentMethod;
import com.nisal.iceflame.enums.PaymentStatus;
import com.nisal.iceflame.exceptions.CartException;
import com.nisal.iceflame.exceptions.OrderException;
import com.nisal.iceflame.mapper.CartMapper;
import com.nisal.iceflame.mapper.OrderMapper;
import com.nisal.iceflame.model.*;
import com.nisal.iceflame.repository.CartRepository;
import com.nisal.iceflame.repository.OrderRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class OrderService {

    private final CartRepository cartRepository;
    private final OrderRepository orderRepository;

    public OrderDto createOrder(Long userId, Long addressId, PaymentMethod paymentMethod) {

        Cart cart = cartRepository.findByUserId(userId)
                .orElseThrow(() -> new CartException("Cart not found", HttpStatus.NOT_FOUND));

        Order order = Order.builder()
                .userId(userId)
                .addressId(addressId)
                .status(OrderStatus.PENDING)
                .paymentStatus(PaymentStatus.PENDING)
                .paymentMethod(paymentMethod)
                .build();

        double total = 0;

        for (CartItem cartItem : cart.getItems()) {

            Product product = cartItem.getProduct();

            OrderItem item = OrderItem.builder()
                    .order(order)
                    .product(product)
                    .quantity(cartItem.getQuantity())
                    .price(product.getPrice())
                    .build();

            order.getItems().add(item);

            total += product.getPrice() * cartItem.getQuantity();
        }

        order.setTotalAmount(total);

        Order saved = orderRepository.save(order);

        return OrderMapper.toDto(saved);
    }

    public OrderDto confirmPayment(Long orderId) {

        Order order = orderRepository.findById(orderId)
                .orElseThrow(() -> new RuntimeException("Order not found"));

        order.setPaymentStatus(PaymentStatus.COMPLETED);
        order.setStatus(OrderStatus.CONFIRMED);

        Cart cart = cartRepository.findByUserId(order.getUserId())
                .orElseThrow(() -> new CartException("Cart not found", HttpStatus.NOT_FOUND));

        cart.getItems().clear();
        cartRepository.save(cart);

        return OrderMapper.toDto(orderRepository.save(order));
    }

    public List<OrderDto> getCurrentOrders(Long userId) {

        List<OrderStatus> statuses = List.of(
                OrderStatus.PENDING,
                OrderStatus.CONFIRMED,
                OrderStatus.PROCESSING,
                OrderStatus.SHIPPED
        );

        List<Order> orders = orderRepository.findByUserIdAndStatusIn(userId, statuses);

        return orders.stream()
                .map(OrderMapper::toDto)
                .toList();
    }

    public List<OrderDto> getPreviousOrders(Long userId) {

        List<OrderStatus> statuses = List.of(
                OrderStatus.DELIVERED,
                OrderStatus.CANCELLED
        );

        List<Order> orders = orderRepository.findByUserIdAndStatusIn(userId, statuses);

        return orders.stream()
                .map(OrderMapper::toDto)
                .toList();
    }

    public List<OrderDto> getOrdersByUser(Long userId) {

        List<Order> orders = orderRepository.findByUserId(userId);

        if (orders.isEmpty()) {
            throw new OrderException("Orders not found", HttpStatus.NOT_FOUND);
        }

        return orders.stream()
                .map(OrderMapper::toDto)
                .toList();
    }
}