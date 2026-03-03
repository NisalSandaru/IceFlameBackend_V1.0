package com.nisal.iceflame.controller;

import com.nisal.iceflame.dto.CategoryDto;
import com.nisal.iceflame.service.CategoryService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/categories")
@RequiredArgsConstructor
public class CategoryController {

    private final CategoryService categoryService;

    // CREATE CATEGORY (Admin only)
    @PostMapping("/add")
    public ResponseEntity<CategoryDto> addCategory(
            @RequestBody CategoryDto dto,
            @RequestParam Long userId // pass admin user ID from request
    ) {
        CategoryDto created = categoryService.addCategory(dto, userId);
        return ResponseEntity.status(HttpStatus.CREATED).body(created);
    }

    // GET ALL CATEGORIES (Active only)
    @GetMapping
    public ResponseEntity<List<CategoryDto>> getAllCategories() {
        List<CategoryDto> list = categoryService.getAllCategories();
        return ResponseEntity.ok(list);
    }

    // GET CATEGORY BY ID (Active only)
    @GetMapping("/{id}")
    public ResponseEntity<CategoryDto> getCategoryById(@PathVariable Long id) {
        CategoryDto category = categoryService.getCategoryById(id);
        return ResponseEntity.ok(category);
    }

    // UPDATE CATEGORY (Admin only)
    @PutMapping("/update/{id}")
    public ResponseEntity<CategoryDto> updateCategory(
            @PathVariable Long id,
            @RequestBody CategoryDto dto,
            @RequestParam Long userId // admin ID
    ) {
        CategoryDto updated = categoryService.updateCategory(id, dto, userId);
        return ResponseEntity.ok(updated);
    }

    // DELETE CATEGORY (Soft delete / Admin only)
    @DeleteMapping("/delete/{id}")
    public ResponseEntity<String> deleteCategory(
            @PathVariable Long id,
            @RequestParam Long userId // admin ID
    ) {
        categoryService.deleteCategory(id, userId);
        return ResponseEntity.ok("Category deleted successfully (soft delete if implemented)");
    }

    // GET ALL CATEGORIES (Admin view including inactive)
    @GetMapping("/admin/all")
    public ResponseEntity<List<CategoryDto>> getAllCategoriesForAdmin() {
        List<CategoryDto> list = categoryService.getAllCategories();
        return ResponseEntity.ok(list);
    }

}