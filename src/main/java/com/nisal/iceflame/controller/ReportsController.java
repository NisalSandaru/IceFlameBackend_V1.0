package com.nisal.iceflame.controller;

import com.nisal.iceflame.service.ReportService;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/reports")
@RequiredArgsConstructor
public class ReportsController {

    private final ReportService reportService;

    @GetMapping("/users/pdf")
    public void usersPdf(
            @RequestParam String from,
            @RequestParam String to,
            HttpServletResponse response
    ) throws Exception {

        response.setContentType("application/pdf");
        response.setHeader("Content-Disposition", "attachment; filename=users.pdf");

        reportService.generateUsersPdf(from, to, response);
    }

    // FUTURE
    @GetMapping("/orders/pdf")
    public void ordersPdf(
            @RequestParam String from,
            @RequestParam String to,
            HttpServletResponse response
    ) throws Exception {

        reportService.generateOrdersPdf(from, to, response);
    }

    @GetMapping("/revenue/pdf")
    public void revenuePdf(
            @RequestParam String from,
            @RequestParam String to,
            HttpServletResponse response
    ) throws Exception {

        reportService.generateRevenuePdf(from, to, response);
    }
}