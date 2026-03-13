package com.nisal.iceflame.service;

import com.nisal.iceflame.dto.OrderDto;
import com.nisal.iceflame.enums.OrderStatus;
import com.nisal.iceflame.enums.PaymentMethod;
import com.nisal.iceflame.enums.PaymentStatus;
import com.nisal.iceflame.exceptions.CartException;
import com.nisal.iceflame.mapper.OrderMapper;
import com.nisal.iceflame.model.*;
import com.nisal.iceflame.repository.CartRepository;
import com.nisal.iceflame.repository.OrderRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class OrderService {

    private final CartRepository cartRepository;
    private final OrderRepository orderRepository;

    public OrderDto checkout(Long userId, Long addressId, PaymentMethod paymentMethod) {

        // Fetch cart
        Cart cart = cartRepository.findByUserId(userId)
                .orElseThrow(() -> new CartException("Cart not found", HttpStatus.NOT_FOUND));

        // Build order
        Order order = Order.builder()
                .userId(userId)
                .addressId(addressId)
                .status(OrderStatus.PENDING)
                .paymentStatus(PaymentStatus.PENDING)
                .paymentMethod(paymentMethod) // store enum value
                .build();

        double total = 0;

        // Convert cart items to order items
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

        // Save order
        Order saved = orderRepository.save(order);

        // Clear cart immediately if COD
        if (paymentMethod == PaymentMethod.COD) {
            cart.getItems().clear();
            cartRepository.save(cart);
        }

        return OrderMapper.toDto(saved);
    }
}