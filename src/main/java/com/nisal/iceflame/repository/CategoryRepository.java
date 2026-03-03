package com.nisal.iceflame.repository;

import com.nisal.iceflame.model.Category;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface CategoryRepository extends JpaRepository<Category, Long> {

    Optional<Category> findByNameIgnoreCase(String name);

    // Only active categories
    List<Category> findAllByIsActiveTrue();

    // Optional: find by ID but only if active
    Optional<Category> findByIdAndIsActiveTrue(Long id);

}