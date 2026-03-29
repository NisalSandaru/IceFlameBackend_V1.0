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
    public ResponseEntity<String> deleteProduct(
            @PathVariable Long productId,
            @PathVariable Long userId) {

        return ResponseEntity.ok(productService.deleteProduct(productId, userId));
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

}