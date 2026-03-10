package com.nisal.iceflame.controller;

import com.nisal.iceflame.dto.CartDto;
import com.nisal.iceflame.dto.WishlistDto;
import com.nisal.iceflame.service.WishlistService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/wishlist")
@RequiredArgsConstructor
public class WishlistController {

    private final WishlistService wishlistService;

    // =====================================
    // GET USER WISHLIST
    // =====================================
    @GetMapping("/{userId}")
    public ResponseEntity<WishlistDto> getWishlist(@PathVariable Long userId) {

        WishlistDto wishlist = wishlistService.getWishlistByUser(userId);

        return ResponseEntity.ok(wishlist);
    }

    // =====================================================
    // ADD item to wishlist
    // =====================================================
    @PostMapping("/add")
    public ResponseEntity<WishlistDto> addToWishlist(
            @RequestParam Long userId,
            @RequestParam Long productId) {

        WishlistDto wishlist = wishlistService.addToWishlist(userId, productId);
        return ResponseEntity.status(HttpStatus.CREATED).body(wishlist);
    }

    // =====================================================
    // REMOVE item from wishlist
    // =====================================================
    @DeleteMapping("/remove")
    public ResponseEntity<WishlistDto> removeFromWishlist(
            @RequestParam Long userId,
            @RequestParam Long productId) {

        WishlistDto wishlist = wishlistService.removeFromWishlist(userId, productId);
        return ResponseEntity.ok(wishlist);
    }
}