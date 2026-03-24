package com.nisal.iceflame.service;

import com.nisal.iceflame.dto.ProductDto;
import com.nisal.iceflame.enums.Role;
import com.nisal.iceflame.exceptions.CategoryException;
import com.nisal.iceflame.exceptions.ProductException;
import com.nisal.iceflame.exceptions.UserException;
import com.nisal.iceflame.mapper.ProductMapper;
import com.nisal.iceflame.model.Category;
import com.nisal.iceflame.model.Product;
import com.nisal.iceflame.model.User;
import com.nisal.iceflame.repository.CategoryRepository;
import com.nisal.iceflame.repository.ProductRepository;
import com.nisal.iceflame.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class ProductService {

    private final ProductRepository productRepository;
    private final CategoryRepository categoryRepository;
    private final UserRepository userRepository;

    // Add product
    public ProductDto addProduct(ProductDto dto, Long userId) {
        User user = userRepository.findById(userId)
                .orElseThrow(() -> new UserException("User not found", HttpStatus.NOT_FOUND));

        if (user.getRole() != Role.ADMIN)
            throw new UserException("Access denied! Only admin can add products.", HttpStatus.FORBIDDEN);

        Category category = categoryRepository.findById(dto.getCategoryId())
                .orElseThrow(() -> new CategoryException("Category not found", HttpStatus.NOT_FOUND));

        if (productRepository.findByNameIgnoreCase(dto.getName()).isPresent()) {
            throw new ProductException("Product already exists", HttpStatus.CONFLICT);
        }

        if (dto.getPrice() <=0 ){
            throw new ProductException("Invalid Product Price", HttpStatus.BAD_REQUEST);
        }

        Product product = ProductMapper.toEntity(dto);
        product.setIsActive(true);
        product.setCategory(category);

        Product saved = productRepository.save(product);
        return ProductMapper.toDto(saved);
    }

    // Update product
    public ProductDto updateProduct(Long productId, ProductDto dto, Long userId) {
        User user = userRepository.findById(userId)
                .orElseThrow(() -> new UserException("User not found", HttpStatus.NOT_FOUND));

        if (user.getRole() != Role.ADMIN)
            throw new UserException("Access denied! Only admin can update products.", HttpStatus.FORBIDDEN);

        if (dto.getPrice() <=0 ){
            throw new ProductException("Invalid Product Price", HttpStatus.BAD_REQUEST);
        }

        Product product = productRepository.findById(productId)
                .orElseThrow(() -> new ProductException("Product not found", HttpStatus.NOT_FOUND));

        // Update fields
        product.setName(dto.getName());
        product.setDescription(dto.getDescription());
        product.setPrice(dto.getPrice());
        product.setIsActive(true);
        if (dto.getRating() != null) {
            product.setPortionSize(dto.getPortionSize());
        }

        if (dto.getRating() != null) {
            product.setRating(dto.getRating());
        }

        // Update images
        if (dto.getImages() != null) {
            product.getImages().clear();
            product.getImages().addAll(
                    dto.getImages().stream()
                            .map(url -> com.nisal.iceflame.model.ProductImage.builder()
                                    .imageUrl(url)
                                    .product(product)
                                    .build())
                            .collect(Collectors.toList())
            );
        }

        // Update category
        if (dto.getCategoryId() != null) {
            Category category = categoryRepository.findById(dto.getCategoryId())
                    .orElseThrow(() -> new CategoryException("Category not found", HttpStatus.NOT_FOUND));
            product.setCategory(category);
        }

        return ProductMapper.toDto(productRepository.save(product));
    }

    // Delete product
    public void deleteProduct(Long productId, Long userId) {
        User user = userRepository.findById(userId)
                .orElseThrow(() -> new UserException("User not found", HttpStatus.NOT_FOUND));

        if (user.getRole() != Role.ADMIN)
            throw new UserException("Access denied! Only admin can delete products.", HttpStatus.FORBIDDEN);

        Product product = productRepository.findById(productId)
                .orElseThrow(() -> new ProductException("Product not found", HttpStatus.NOT_FOUND));

        // Optional: check relations like orders here
        productRepository.delete(product);
    }

    // Get all active products
    public List<ProductDto> getAllActiveProducts() {
        return productRepository.findByIsActiveTrue()
                .stream()
                .map(ProductMapper::toDto)
                .collect(Collectors.toList());
    }

    // Get products by category
    public List<ProductDto> getProductsByCategory(Long categoryId) {
        return productRepository.findByCategoryIdAndIsActiveTrue(categoryId)
                .stream()
                .map(ProductMapper::toDto)
                .collect(Collectors.toList());
    }

    // Search products by name
    public List<ProductDto> searchProducts(String keyword) {
        return productRepository.findByNameContainingIgnoreCaseAndIsActiveTrue(keyword)
                .stream()
                .map(ProductMapper::toDto)
                .collect(Collectors.toList());
    }

    // Get single product by ID
    public ProductDto getProductById(Long id) {
        return productRepository.findById(id)
                .map(ProductMapper::toDto)
                .orElseThrow(() -> new ProductException("Product not found", HttpStatus.NOT_FOUND));
    }
}
