package com.nisal.iceflame.service;

import com.nisal.iceflame.dto.CartDto;
import com.nisal.iceflame.exceptions.CartException;
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
import lombok.NoArgsConstructor;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class CartService {

    private final CartRepository cartRepository;
    private final CartItemRepository cartItemRepository;
    private final ProductRepository productRepository;
    private final UserRepository userRepository;

    // =====================================================
    // 🔥 ADD TO CART
    // =====================================================
    public CartDto addToCart(Long userId, Long productId, int quantity) {

        User user = userRepository.findById(userId)
                .orElseThrow(() -> new UserException("User not found", HttpStatus.NOT_FOUND));

        Product product = productRepository.findById(productId)
                .orElseThrow(() -> new ProductException("Product not found", HttpStatus.NOT_FOUND));

        if (user.getIsActive()==false){
            throw new UserException("User is inactive!", HttpStatus.FORBIDDEN);
        }
        Cart cart = cartRepository.findByUserId(userId)
                .orElseGet(() -> {
                    Cart newCart = Cart.builder()
                            .user(user)
                            .build();
                    return cartRepository.save(newCart);
                });

        Optional<CartItem> existingItem =
                cartItemRepository.findByCartAndProduct(cart, product);

        if (existingItem.isPresent()) {

            CartItem item = existingItem.get();
            item.setQuantity(item.getQuantity() + quantity);

        } else {

            CartItem newItem = CartItem.builder()
                    .cart(cart)
                    .product(product)
                    .quantity(quantity)
                    .build();

            cartItemRepository.save(newItem);
        }

        return CartMapper.toDto(cart);
    }

    // =====================================================
    // 🔥 GET CART
    // =====================================================
    public CartDto getCartByUser(Long userId) {

        Cart cart = cartRepository.findByUserId(userId)
                .orElseThrow(() -> new CartException("Cart not found", HttpStatus.NOT_FOUND));

        return CartMapper.toDto(cart);
    }

    // =====================================================
    // 🔥 UPDATE QUANTITY
    // =====================================================
    public CartDto updateQuantity(Long cartItemId, int quantity) {

        CartItem item = cartItemRepository.findById(cartItemId)
                .orElseThrow(() -> new RuntimeException("Cart item not found"));

        if (quantity <= 0) {
            cartItemRepository.delete(item);
        } else {
            item.setQuantity(quantity);
        }

        return cartMapper.toDto(item.getCart());
    }

    // =====================================================
    // 🔥 REMOVE ITEM
    // =====================================================
    public CartDto removeItem(Long cartItemId) {

        CartItem item = cartItemRepository.findById(cartItemId)
                .orElseThrow(() -> new RuntimeException("Cart item not found"));

        Cart cart = item.getCart();

        cartItemRepository.delete(item);

        return cartMapper.toDto(cart);
    }

    // =====================================================
    // 🔥 CLEAR CART
    // =====================================================
    public void clearCart(Long userId) {

        Cart cart = cartRepository.findByUserId(userId)
                .orElseThrow(() -> new RuntimeException("Cart not found"));

        cartItemRepository.deleteAll(cart.getItems());
    }
}
