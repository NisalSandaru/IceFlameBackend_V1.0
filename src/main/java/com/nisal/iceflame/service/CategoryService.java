package com.nisal.iceflame.service;

import com.nisal.iceflame.dto.CategoryDto;
import com.nisal.iceflame.enums.Role;
import com.nisal.iceflame.exceptions.CategoryException;
import com.nisal.iceflame.exceptions.UserException;
import com.nisal.iceflame.mapper.CategoryMapper;
import com.nisal.iceflame.model.Category;
import com.nisal.iceflame.model.User;
import com.nisal.iceflame.repository.CategoryRepository;
import com.nisal.iceflame.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class CategoryService {

    private final CategoryRepository categoryRepository;
    private final UserRepository userRepository;

    public CategoryDto addCategory(CategoryDto dto, Long userId){

        User user = userRepository.findById(userId).orElseThrow(
                ()-> new UserException("User not Found", HttpStatus.NOT_FOUND)
        );

        if (user.getRole() != Role.ADMIN) {
            throw new UserException("Access denied!", HttpStatus.FORBIDDEN);
        }

        if (categoryRepository.findByNameIgnoreCase(dto.getName()).isPresent()) {
            throw new CategoryException("Category already exists", HttpStatus.CONFLICT);
        }

        Category category = CategoryMapper.toEntity(dto);
        category.setIsActive(true);
        category = categoryRepository.save(category);

        return CategoryMapper.toDto(category);

    }

    // Get all categories (no role restriction, anyone can see)
    public List<CategoryDto> getAllCategories() {
        return categoryRepository.findAll()
                .stream()
                .map(CategoryMapper::toDto)
                .toList();
    }

    // Get single category by ID
    public CategoryDto getCategoryById(Long categoryId) {
        Category category = categoryRepository.findById(categoryId)
                .orElseThrow(() -> new CategoryException("Category not found", HttpStatus.NOT_FOUND));

        return CategoryMapper.toDto(category);
    }

    // Update category
    public CategoryDto updateCategory(Long categoryId, CategoryDto dto, Long userId) {
        User user = userRepository.findById(userId)
                .orElseThrow(() -> new UserException("User not found", HttpStatus.NOT_FOUND));

        if (user.getRole() != Role.ADMIN) {
            throw new UserException("Access denied!", HttpStatus.FORBIDDEN);
        }

        Category category = categoryRepository.findById(categoryId)
                .orElseThrow(() -> new CategoryException("Category not found", HttpStatus.NOT_FOUND));

        // Optional: check for duplicate name if changing name
        if (!category.getName().equalsIgnoreCase(dto.getName()) &&
                categoryRepository.findByNameIgnoreCase(dto.getName()).isPresent()) {
            throw new CategoryException("Category name already exists", HttpStatus.CONFLICT);
        }

        category.setName(dto.getName());
        category.setCatImageUrl(dto.getCatImageUrl()); // your field
        category = categoryRepository.save(category);

        return CategoryMapper.toDto(category);
    }

    // Get all active categories
    public List<CategoryDto> getAllActiveCategories() {
        return categoryRepository.findAllByIsActiveTrue()
                .stream()
                .map(CategoryMapper::toDto)
                .toList();
    }

    // Get category by ID (active only)
    public CategoryDto getActiveCategoryById(Long categoryId) {
        Category category = categoryRepository.findByIdAndIsActiveTrue(categoryId)
                .orElseThrow(() -> new CategoryException("Category not found", HttpStatus.NOT_FOUND));

        return CategoryMapper.toDto(category);
    }

    // Delete category
    public void deleteCategory(Long categoryId, Long userId) {
        User user = userRepository.findById(userId)
                .orElseThrow(() -> new UserException("User not found", HttpStatus.NOT_FOUND));

        if (user.getRole() != Role.ADMIN) {
            throw new UserException("Access denied!", HttpStatus.FORBIDDEN);
        }

        Category category = categoryRepository.findById(categoryId)
                .orElseThrow(() -> new CategoryException("Category not found", HttpStatus.NOT_FOUND));

        // If there are no relations (like products) → safe to delete
        categoryRepository.delete(category);
    }

}
