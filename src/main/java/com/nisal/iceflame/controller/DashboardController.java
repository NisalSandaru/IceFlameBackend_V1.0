package com.nisal.iceflame.controller;

import com.nisal.iceflame.model.Order;
import com.nisal.iceflame.model.User;
import com.nisal.iceflame.repository.OrderRepository;
import com.nisal.iceflame.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.time.Month;
import java.time.format.TextStyle;
import java.util.*;

@RestController
@RequestMapping("/dashboard")
@RequiredArgsConstructor
public class DashboardController {

    private final UserRepository userRepository;
    private final OrderRepository orderRepository;

    @GetMapping
    public Map<String, Object> getDashboardStats() {

        Map<String, Object> res = new HashMap<>();

        // ======================
        // 👤 USER DATA
        // ======================
        List<User> users = userRepository.findAll();

        long active = users.stream()
                .filter(User::getIsActive)
                .count();

        long inactive = users.size() - active;

        res.put("totalUsers", users.size());
        res.put("activeUsers", active);
        res.put("inactiveUsers", inactive);

        // ======================
        // 📦 ORDER DATA
        // ======================
        List<Order> orders = orderRepository.findAll();

        res.put("totalOrders", orders.size());

        // ======================
        // 💰 REVENUE (TOTAL)
        // ======================
        double revenue = orders.stream()
                .filter(o -> o.getTotalAmount() != null)
                .mapToDouble(Order::getTotalAmount)
                .sum();

        res.put("revenue", revenue);

        // ======================
        // 📊 MONTHLY REVENUE
        // ======================
        Map<String, Double> monthlyMap = new LinkedHashMap<>();

        for (Order o : orders) {
            if (o.getCreatedAt() == null || o.getTotalAmount() == null) continue;

            Month month = o.getCreatedAt().getMonth();
            String monthName = month.getDisplayName(TextStyle.SHORT, Locale.ENGLISH);

            monthlyMap.put(monthName,
                    monthlyMap.getOrDefault(monthName, 0.0) + o.getTotalAmount());
        }

        List<Map<String, Object>> monthlyRevenue = new ArrayList<>();

        for (String month : monthlyMap.keySet()) {
            monthlyRevenue.add(Map.of(
                    "month", month,
                    "revenue", monthlyMap.get(month)
            ));
        }

        res.put("monthlyRevenue", monthlyRevenue);

        // ======================
        // 📈 WEEKLY ORDERS
        // ======================
        Map<String, Long> weeklyMap = new LinkedHashMap<>();

        for (Order o : orders) {
            if (o.getCreatedAt() == null) continue;

            String day = o.getCreatedAt()
                    .getDayOfWeek()
                    .getDisplayName(TextStyle.SHORT, Locale.ENGLISH);

            weeklyMap.put(day,
                    weeklyMap.getOrDefault(day, 0L) + 1);
        }

        List<Map<String, Object>> weeklyOrders = new ArrayList<>();

        for (String day : weeklyMap.keySet()) {
            weeklyOrders.add(Map.of(
                    "name", day,
                    "orders", weeklyMap.get(day)
            ));
        }

        res.put("weeklyOrders", weeklyOrders);

        return res;
    }
}