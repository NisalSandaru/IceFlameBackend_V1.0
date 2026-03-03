package com.nisal.iceflame.repository;

import com.nisal.iceflame.model.Category;
import com.nisal.iceflame.model.Product;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface ProductRepository extends JpaRepository<Product, Long> {

    Optional<Category> findByNameIgnoreCase(String name);

    // Get all active products
    List<Product> findByIsActiveTrue();

    // Get products by category
    List<Product> findByCategoryIdAndIsActiveTrue(Long categoryId);

    // Optional: search by name
    List<Product> findByNameContainingIgnoreCaseAndIsActiveTrue(String name);
}
