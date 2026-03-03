package com.nisal.iceflame.repository;

import com.nisal.iceflame.model.ProductImage;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ProductImageRepository extends JpaRepository<ProductImage, Long> {

    // Find all images of a product
    List<ProductImage> findByProductId(Long productId);
}
