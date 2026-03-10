package com.nisal.iceflame.service;

import com.nisal.iceflame.dto.CartDto;
import com.nisal.iceflame.dto.WishlistDto;
import com.nisal.iceflame.exceptions.CartException;
import com.nisal.iceflame.exceptions.ProductException;
import com.nisal.iceflame.exceptions.UserException;
import com.nisal.iceflame.exceptions.WishlistException;
import com.nisal.iceflame.mapper.CartMapper;
import com.nisal.iceflame.mapper.WishlistMapper;
import com.nisal.iceflame.model.*;
import com.nisal.iceflame.repository.ProductRepository;
import com.nisal.iceflame.repository.UserRepository;
import com.nisal.iceflame.repository.WishlistItemRepository;
import com.nisal.iceflame.repository.WishlistRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
@RequiredArgsConstructor
public class WishlistService {

    private final WishlistRepository wishlistRepository;
    private final WishlistItemRepository wishlistItemRepository;
    private final ProductRepository productRepository;
    private final UserRepository userRepository;

    // =====================================================
    // ADD TO Wishlist
    // =====================================================
    public WishlistDto addToWishlist(Long userId, Long productId) {

        User user = getUser(userId);
        Product product = getProduct(productId);

        Wishlist wishlist = getOrCreateWishlist(user);

        Optional<WishlistItem> existingItem =
                wishlistItemRepository.findByWishlistAndProduct(wishlist, product);

        if (existingItem.isPresent()) {

            return WishlistMapper.toDto(wishlist);

        } else {

            WishlistItem item = WishlistItem.builder()
                    .wishlist(wishlist)
                    .product(product)
                    .build();
            wishlistItemRepository.save(item);
        }

        return WishlistMapper.toDto(getWishlist(wishlist.getId()));
    }

    // =====================================================
    // GET WISHLIST
    // =====================================================
    public WishlistDto getWishlistByUser(Long userId) {

        Wishlist wishlist = wishlistRepository.findByUserId(userId)
                .orElseThrow(() ->
                        new WishlistException("Wishlist not found", HttpStatus.NOT_FOUND));

        return WishlistMapper.toDto(wishlist);
    }

    public WishlistDto removeFromWishlist(Long userId, Long productId) {
        User user = getUser(userId);
        Wishlist wishlist = getOrCreateWishlist(user);
        Product product = getProduct(productId);

        WishlistItem item = wishlistItemRepository
                .findByWishlistAndProduct(wishlist, product)
                .orElseThrow(() -> new WishlistException("Item not in wishlist", HttpStatus.NOT_FOUND));

        wishlistItemRepository.delete(item);
        wishlist.getItems().remove(item);

        return WishlistMapper.toDto(wishlist);
    }

    private User getUser(Long userId) {

        User user = userRepository.findById(userId)
                .orElseThrow(() ->
                        new UserException("User not found", HttpStatus.NOT_FOUND));

        if (!user.getIsActive()) {
            throw new UserException("User is inactive", HttpStatus.FORBIDDEN);
        }

        return user;
    }

    private Product getProduct(Long productId) {

        return productRepository.findById(productId)
                .orElseThrow(() ->
                        new ProductException("Product not found", HttpStatus.NOT_FOUND));
    }

    private Wishlist getOrCreateWishlist(User user) {

        return wishlistRepository.findByUserId(user.getId())
                .orElseGet(() -> {

                    Wishlist newWishlist = Wishlist.builder()
                            .user(user)
                            .build();

                    return wishlistRepository.save(newWishlist);
                });
    }

    private Wishlist getWishlist(Long wishlistId) {

        return wishlistRepository.findById(wishlistId)
                .orElseThrow(() ->
                        new WishlistException("Wishlist not found", HttpStatus.NOT_FOUND));
    }
}
