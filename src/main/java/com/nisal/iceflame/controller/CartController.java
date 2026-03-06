package com.nisal.iceflame.controller;

import com.nisal.iceflame.dto.AddToCartRequest;
import com.nisal.iceflame.dto.CartDto;
import com.nisal.iceflame.service.CartService;

import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/cart")
@RequiredArgsConstructor
public class CartController {

    private final CartService cartService;

    // =====================================
    // ADD PRODUCT TO CART
    // =====================================
    @PostMapping("/add")
    public ResponseEntity<CartDto> addToCart(
            @RequestParam Long userId,
            @RequestBody AddToCartRequest request
            ) {

        CartDto cart = cartService.addToCart(userId, request.getProductId(), request.getQuantity());

        return ResponseEntity.status(HttpStatus.CREATED).body(cart);
    }

    // =====================================
    // GET USER CART
    // =====================================
    @GetMapping("/{userId}")
    public ResponseEntity<CartDto> getCart(@PathVariable Long userId) {

        CartDto cart = cartService.getCartByUser(userId);

        return ResponseEntity.ok(cart);
    }

    // =====================================
    // UPDATE QUANTITY (+ / -)
    // =====================================
    @PutMapping("/update")
    public ResponseEntity<CartDto> updateQuantity(
            @RequestParam Long cartItemId,
            @RequestParam int quantity
    ) {

        CartDto cart = cartService.updateQuantity(cartItemId, quantity);

        return ResponseEntity.ok(cart);
    }

    // =====================================
    // REMOVE ITEM
    // =====================================
    @DeleteMapping("/remove/{cartItemId}")
    public ResponseEntity<CartDto> removeItem(@PathVariable Long cartItemId) {

        CartDto cart = cartService.removeItem(cartItemId);

        return ResponseEntity.ok(cart);
    }

    // =====================================
    // CLEAR CART
    // =====================================
    @DeleteMapping("/clear/{userId}")
    public ResponseEntity<String> clearCart(@PathVariable Long userId) {

        cartService.clearCart(userId);

        return ResponseEntity.ok("Cart cleared successfully");
    }
}