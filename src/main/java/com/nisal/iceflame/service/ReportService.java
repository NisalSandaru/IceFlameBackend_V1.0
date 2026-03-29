package com.nisal.iceflame.service;

import com.itextpdf.kernel.pdf.PdfDocument;
import com.itextpdf.kernel.pdf.PdfWriter;
import com.itextpdf.layout.Document;
import com.itextpdf.layout.element.*;
import com.itextpdf.layout.properties.TextAlignment;
import com.nisal.iceflame.model.Order;
import com.nisal.iceflame.model.User;
import com.nisal.iceflame.repository.OrderRepository;
import com.nisal.iceflame.repository.UserRepository;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;
import java.util.List;

@Service
@RequiredArgsConstructor
public class ReportService {

    private final UserRepository userRepository;
    private final OrderRepository orderRepository;

    public void generateUsersPdf(String from, String to, HttpServletResponse response) throws Exception {

        PdfWriter writer = new PdfWriter(response.getOutputStream());
        PdfDocument pdf = new PdfDocument(writer);
        Document document = new Document(pdf);

        // TITLE
        document.add(
                new Paragraph("Users Report")
                        .setBold()
                        .setFontSize(18)
                        .setTextAlignment(TextAlignment.CENTER)
        );

        document.add(new Paragraph("From: " + from + " To: " + to));
        document.add(new Paragraph("\n"));

        // 🔥 CONVERT STRING → LocalDateTime
        LocalDateTime fromDateTime = LocalDate.parse(from).atStartOfDay();
        LocalDateTime toDateTime = LocalDate.parse(to).atTime(LocalTime.MAX);

        // 🔥 FETCH FILTERED DATA
        List<User> users = userRepository.findByCreatedAtBetween(fromDateTime, toDateTime);

        // TABLE
        Table table = new Table(5);

        table.addHeaderCell("ID");
        table.addHeaderCell("Name");
        table.addHeaderCell("Email");
        table.addHeaderCell("Mobile");
        table.addHeaderCell("Status");

        for (User u : users) {
            table.addCell(String.valueOf(u.getId()));
            table.addCell(u.getFullName());
            table.addCell(u.getEmail());
            table.addCell(u.getMobileNumber() != null ? u.getMobileNumber() : "-");
            table.addCell(u.getIsActive() ? "Active" : "Inactive");
        }

        document.add(table);
        document.close();
    }

    // PLACEHOLDERS (no error)
    public void generateOrdersPdf(String from, String to, HttpServletResponse response) throws Exception {

        PdfWriter writer = new PdfWriter(response.getOutputStream());
        PdfDocument pdf = new PdfDocument(writer);
        Document document = new Document(pdf);

        // TITLE
        document.add(
                new Paragraph("Orders Report")
                        .setBold()
                        .setFontSize(18)
                        .setTextAlignment(TextAlignment.CENTER)
        );

        document.add(new Paragraph("From: " + from + " To: " + to));
        document.add(new Paragraph("\n"));

        // DATE CONVERSION
        LocalDateTime fromDateTime = LocalDate.parse(from).atStartOfDay();
        LocalDateTime toDateTime = LocalDate.parse(to).atTime(LocalTime.MAX);

        // FETCH DATA
        List<Order> orders = orderRepository.findByCreatedAtBetween(fromDateTime, toDateTime);

        // TABLE
        Table table = new Table(5);

        table.addHeaderCell("Order ID");
        table.addHeaderCell("User ID");
        table.addHeaderCell("Amount");
        table.addHeaderCell("Status");
        table.addHeaderCell("Date");

        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm");

        for (Order o : orders) {

            table.addCell(String.valueOf(o.getId()));
            table.addCell(String.valueOf(o.getUserId()));

            table.addCell(o.getTotalAmount() != null ? String.valueOf(o.getTotalAmount()) : "-");

            table.addCell(o.getStatus() != null ? o.getStatus().toString() : "-");

            table.addCell(
                    o.getCreatedAt() != null
                            ? o.getCreatedAt().format(formatter)
                            : "-"
            );
        }

        document.add(table);

        // SUMMARY
        double totalRevenue = orders.stream()
                .filter(o -> o.getTotalAmount() != null)
                .mapToDouble(Order::getTotalAmount)
                .sum();

        document.add(new Paragraph("\n"));
        document.add(new Paragraph("Total Orders: " + orders.size()));
        document.add(new Paragraph("Total Revenue: Rs. " + totalRevenue));

        document.close();
    }

    public void generateRevenuePdf(String from, String to, HttpServletResponse response) throws Exception {

        PdfWriter writer = new PdfWriter(response.getOutputStream());
        PdfDocument pdf = new PdfDocument(writer);
        Document document = new Document(pdf);

        // TITLE
        document.add(
                new Paragraph("Revenue Report")
                        .setBold()
                        .setFontSize(18)
                        .setTextAlignment(TextAlignment.CENTER)
        );

        document.add(new Paragraph("From: " + from + " To: " + to));
        document.add(new Paragraph("\n"));

        // DATE CONVERSION
        LocalDateTime fromDateTime = LocalDate.parse(from).atStartOfDay();
        LocalDateTime toDateTime = LocalDate.parse(to).atTime(LocalTime.MAX);

        // FETCH ORDERS
        List<Order> orders = orderRepository.findByCreatedAtBetween(fromDateTime, toDateTime);

        // FILTER PAID ORDERS (IMPORTANT 🔥)
        List<Order> paidOrders = orders.stream()
                .filter(o -> o.getTotalAmount() != null)
                .toList();

        // TOTAL REVENUE
        double totalRevenue = paidOrders.stream()
                .mapToDouble(Order::getTotalAmount)
                .sum();

        // TABLE
        Table table = new Table(3);

        table.addHeaderCell("Order ID");
        table.addHeaderCell("Date");
        table.addHeaderCell("Amount");

        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd MMM yyyy, HH:mm");

        for (Order o : paidOrders) {

            table.addCell(String.valueOf(o.getId()));

            table.addCell(
                    o.getCreatedAt() != null
                            ? o.getCreatedAt().format(formatter)
                            : "-"
            );

            table.addCell(String.valueOf(o.getTotalAmount()));
        }

        document.add(table);

        // SUMMARY SECTION 🔥
        document.add(new Paragraph("\n"));
        document.add(
                new Paragraph("Total Orders: " + paidOrders.size())
                        .setBold()
        );
        document.add(
                new Paragraph("Total Revenue: Rs. " + totalRevenue)
                        .setBold()
        );

        document.close();
    }


}