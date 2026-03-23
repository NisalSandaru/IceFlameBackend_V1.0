package com.nisal.iceflame.controller;

import com.nisal.iceflame.dto.ProductDto;
import com.nisal.iceflame.service.ProductService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("/products")
@RequiredArgsConstructor
public class ProductController {

    private final ProductService productService;

    @PostMapping("/add/{userId}")
    public ResponseEntity<ProductDto> addProduct(@PathVariable Long userId, @RequestBody ProductDto dto) {
        return ResponseEntity.ok(productService.addProduct(dto, userId));
    }

    @PutMapping("/update/{productId}/{userId}")
    public ResponseEntity<ProductDto> updateProduct(@PathVariable Long productId,
                                                    @PathVariable Long userId,
                                                    @RequestBody ProductDto dto) {
        return ResponseEntity.ok(productService.updateProduct(productId, dto, userId));
    }

    @DeleteMapping("/delete/{productId}/{userId}")
    public ResponseEntity<String> deleteProduct(@PathVariable Long productId, @PathVariable Long userId) {
        productService.deleteProduct(productId, userId);
        return ResponseEntity.ok("Product deleted successfully");
    }

    @GetMapping
    public ResponseEntity<List<ProductDto>> getAllProducts() {
        return ResponseEntity.ok(productService.getAllActiveProducts());
    }

    @GetMapping("/category/{categoryId}")
    public ResponseEntity<List<ProductDto>> getProductsByCategory(@PathVariable Long categoryId) {
        return ResponseEntity.ok(productService.getProductsByCategory(categoryId));
    }

    @GetMapping("/search")
    public ResponseEntity<List<ProductDto>> searchProducts(@RequestParam String keyword) {
        return ResponseEntity.ok(productService.searchProducts(keyword));
    }

    @GetMapping("/{id}")
    public ResponseEntity<ProductDto> getProduct(@PathVariable Long id) {
        return ResponseEntity.ok(productService.getProductById(id));
    }

    @PostMapping("/upload")
    public ResponseEntity<List<String>> uploadImages(@RequestParam("files") MultipartFile[] files) {
        List<String> urls = new ArrayList<>();

        try {
            String uploadDir = "D:/Work file/Springboot test learning/Apps/IceFlame/uploads/";

            File dir = new File(uploadDir);
            if (!dir.exists()) {
                dir.mkdirs();
            }

            for (MultipartFile file : files) {

                // ✅ FIX: remove spaces
                String original = file.getOriginalFilename().replaceAll(" ", "_");
                String fileName = UUID.randomUUID() + "_" + original;

                File destination = new File(uploadDir + fileName);
                file.transferTo(destination);

                // ✅ URL must match WebConfig
                String fileUrl = "http://localhost:8080/v1.0/uploads/" + fileName;

                urls.add(fileUrl);

                System.out.println("SAVED FILE PATH: " + destination.getAbsolutePath());
            }

            return ResponseEntity.ok(urls);

        } catch (Exception e) {
            e.printStackTrace();
            return ResponseEntity.status(500).body(null);
        }
    }
}