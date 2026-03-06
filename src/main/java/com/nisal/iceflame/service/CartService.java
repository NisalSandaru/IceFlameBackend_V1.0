package com.nisal.iceflame.service;

import com.nisal.iceflame.dto.CartDto;
import com.nisal.iceflame.exceptions.CartException;
import com.nisal.iceflame.exceptions.CartItemException;
import com.nisal.iceflame.exceptions.ProductException;
import com.nisal.iceflame.exceptions.UserException;
import com.nisal.iceflame.mapper.CartMapper;
import com.nisal.iceflame.model.Cart;
import com.nisal.iceflame.model.CartItem;
import com.nisal.iceflame.model.Product;
import com.nisal.iceflame.model.User;
import com.nisal.iceflame.repository.CartItemRepository;
import com.nisal.iceflame.repository.CartRepository;
import com.nisal.iceflame.repository.ProductRepository;
import com.nisal.iceflame.repository.UserRepository;

import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
@RequiredArgsConstructor
public class CartService {

    private final CartRepository cartRepository;
    private final CartItemRepository cartItemRepository;
    private final ProductRepository productRepository;
    private final UserRepository userRepository;

    // =====================================================
    // ADD TO CART
    // =====================================================
    public CartDto addToCart(Long userId, Long productId, int quantity) {

        if (quantity <= 0) {
            throw new CartException("Quantity must be greater than 0", HttpStatus.BAD_REQUEST);
        }

        User user = getUser(userId);
        Product product = getProduct(productId);

        Cart cart = getOrCreateCart(user);

        Optional<CartItem> existingItem =
                cartItemRepository.findByCartAndProduct(cart, product);

        if (existingItem.isPresent()) {

            CartItem item = existingItem.get();
            item.setQuantity(item.getQuantity() + quantity);
            cartItemRepository.save(item);

        } else {

            CartItem newItem = CartItem.builder()
                    .cart(cart)
                    .product(product)
                    .quantity(quantity)
                    .build();

            cartItemRepository.save(newItem);
        }

        return CartMapper.toDto(getCart(cart.getId()));
    }

    // =====================================================
    // GET CART
    // =====================================================
    public CartDto getCartByUser(Long userId) {

        Cart cart = cartRepository.findByUserId(userId)
                .orElseThrow(() ->
                        new CartException("Cart not found", HttpStatus.NOT_FOUND));

        return CartMapper.toDto(cart);
    }

    // =====================================================
    // UPDATE QUANTITY
    // =====================================================
    public CartDto updateQuantity(Long cartItemId, int quantity) {

        CartItem item = cartItemRepository.findById(cartItemId)
                .orElseThrow(() ->
                        new CartItemException("Cart item not found", HttpStatus.NOT_FOUND));

        if (quantity <= 0) {

            cartItemRepository.delete(item);

        } else {

            item.setQuantity(quantity);
            cartItemRepository.save(item);
        }

        return CartMapper.toDto(getCart(item.getCart().getId()));
    }

    // =====================================================
    // REMOVE ITEM
    // =====================================================
    public CartDto removeItem(Long cartItemId) {

        CartItem item = cartItemRepository.findById(cartItemId)
                .orElseThrow(() ->
                        new CartItemException("Cart item not found", HttpStatus.NOT_FOUND));

        Long cartId = item.getCart().getId();

        cartItemRepository.delete(item);

        return CartMapper.toDto(getCart(cartId));
    }

    // =====================================================
    // CLEAR CART
    // =====================================================
    public void clearCart(Long userId) {

        Cart cart = cartRepository.findByUserId(userId)
                .orElseThrow(() ->
                        new CartException("Cart not found", HttpStatus.NOT_FOUND));

        cartItemRepository.deleteAll(cart.getItems());
    }

    // =====================================================
    // PRIVATE HELPERS
    // =====================================================

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

    private Cart getOrCreateCart(User user) {

        return cartRepository.findByUserId(user.getId())
                .orElseGet(() -> {

                    Cart newCart = Cart.builder()
                            .user(user)
                            .build();

                    return cartRepository.save(newCart);
                });
    }

    private Cart getCart(Long cartId) {

        return cartRepository.findById(cartId)
                .orElseThrow(() ->
                        new CartException("Cart not found", HttpStatus.NOT_FOUND));
    }
}