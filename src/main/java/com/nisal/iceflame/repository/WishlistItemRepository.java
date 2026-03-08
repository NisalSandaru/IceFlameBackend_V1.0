package com.nisal.iceflame.repository;

import com.nisal.iceflame.model.*;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface WishlistItemRepository extends JpaRepository<WishlistItem, Long> {

    Optional<WishlistItem> findByWishlistAndProduct(Wishlist wishlist, Product product);

    List<WishlistItem> findByWishlist(Wishlist wishlist);

}
