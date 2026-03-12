package com.nisal.iceflame.model;

import com.nisal.iceflame.enums.OrderStatus;
import com.nisal.iceflame.enums.PaymentStatus;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.CreationTimestamp;

import java.time.LocalDateTime;

@Entity
@Table(name = "orders")
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Order {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private Long userId;

    private Long addressId;

    private Double totalAmount;

    @Enumerated(EnumType.STRING)
    private OrderStatus status; // PENDING, PAID, etc.

    @Enumerated(EnumType.STRING)
    private PaymentStatus paymentStatus; // PENDING, COMPLETED

    @CreationTimestamp
    @Column(updatable = false)
    private LocalDateTime createdAt;
}
