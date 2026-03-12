package com.nisal.iceflame.enums;

public enum OrderStatus {
    PENDING,        // Just created, payment pending
    PAID,           // Payment completed
    PROCESSING,     // Order being processed
    SHIPPED,        // On the way
    DELIVERED,      // Delivered to customer
    CANCELLED       // Order cancelled
}
