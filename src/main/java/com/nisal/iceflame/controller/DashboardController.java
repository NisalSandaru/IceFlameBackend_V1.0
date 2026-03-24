package com.nisal.iceflame.controller;

import com.nisal.iceflame.model.User;
import com.nisal.iceflame.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.*;

@RestController
@RequestMapping("/dashboard")
@RequiredArgsConstructor
public class DashboardController {

    private final UserRepository userRepository;

    @GetMapping
    public Map<String, Object> getDashboardStats() {

        List<User> users = userRepository.findAll();

        long active = users.stream().filter(User::getIsActive).count();
        long inactive = users.size() - active;

        Map<String, Object> res = new HashMap<>();

        // USER DATA
        res.put("totalUsers", users.size());
        res.put("activeUsers", active);
        res.put("inactiveUsers", inactive);

        // 👉 TEMP MOCK DATA (until you have Order/Payment tables)
        res.put("totalOrders", 75);
        res.put("revenue", 2500);

        // 📈 Monthly Revenue
        List<Map<String, Object>> monthlyRevenue = List.of(
                Map.of("month", "Jan", "revenue", 500),
                Map.of("month", "Feb", "revenue", 800),
                Map.of("month", "Mar", "revenue", 1200),
                Map.of("month", "Apr", "revenue", 900),
                Map.of("month", "May", "revenue", 1500)
        );

        // 📊 Weekly Orders
        List<Map<String, Object>> weeklyOrders = List.of(
                Map.of("name", "Mon", "orders", 10),
                Map.of("name", "Tue", "orders", 20),
                Map.of("name", "Wed", "orders", 15),
                Map.of("name", "Thu", "orders", 30),
                Map.of("name", "Fri", "orders", 25)
        );

        res.put("monthlyRevenue", monthlyRevenue);
        res.put("weeklyOrders", weeklyOrders);

        return res;
    }
}