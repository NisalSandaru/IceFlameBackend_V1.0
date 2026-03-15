package com.nisal.iceflame.repository;

import com.nisal.iceflame.enums.OrderStatus;
import com.nisal.iceflame.model.Order;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface OrderRepository extends JpaRepository<Order, Long> {

    List<Order> findByUserId(Long userId);
    List<Order> findByUserIdAndStatusIn(Long userId, List<OrderStatus> statuses);

}
