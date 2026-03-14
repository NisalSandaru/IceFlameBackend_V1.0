package com.nisal.iceflame.enums;

public enum OrderStatus {
    PENDING,        // Order created
    CONFIRMED,      // Payment confirmed
    PROCESSING,     // Preparing order
    SHIPPED,        // Sent to delivery
    DELIVERED,      // Delivered to customer
    CANCELLED       // Cancelled
}
