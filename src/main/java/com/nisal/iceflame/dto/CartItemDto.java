package com.nisal.iceflame.dto;

import com.nisal.iceflame.model.Cart;
import com.nisal.iceflame.model.Product;
import jakarta.persistence.ManyToOne;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class CartItemDto {

    private Long id;
    private Cart cart;
    private Product product;
    private int quantity;
}
