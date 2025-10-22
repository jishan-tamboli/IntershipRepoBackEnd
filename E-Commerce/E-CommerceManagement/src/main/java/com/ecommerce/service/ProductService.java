package com.ecommerce.service;

import com.ecommerce.entity.Category;
import com.ecommerce.entity.Product;
import com.ecommerce.repository.CategoryRepository;
import com.ecommerce.repository.ProductRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.util.List;
import java.util.Optional;

@Service
@Transactional
public class ProductService {

    @Autowired private ProductRepository productRepo;
    @Autowired private CategoryRepository categoryRepo;

    public Product createProduct(Product product) {
        System.out.println("=== CREATING PRODUCT ===");
        System.out.println("Received: " + product.getProductName());
        System.out.println("Category ID: " + (product.getCategory() != null ? product.getCategory().getCategoryId() : "NULL"));
        
        // VALIDATION
        if (product.getProductName() == null || product.getProductName().trim().isEmpty()) {
            throw new RuntimeException("Product name is required");
        }
        if (product.getPrice() == null || product.getPrice().compareTo(BigDecimal.ZERO) <= 0) {
            throw new RuntimeException("Price must be positive");
        }
        if (product.getStockQuantity() == null || product.getStockQuantity() < 0) {
            throw new RuntimeException("Stock quantity cannot be negative");
        }
        
        // CATEGORY - CRITICAL FIX
        Integer catId = product.getCategory() != null ? product.getCategory().getCategoryId() : null;
        if (catId == null || catId <= 0) {
            throw new RuntimeException("Valid category is required");
        }
        
        Category category = categoryRepo.findById(catId)
                .orElseThrow(() -> new RuntimeException("Category with ID " + catId + " not found"));
        
        product.setCategory(category);
        product.setStatus(true);
        
        System.out.println("Saving product...");
        Product saved = productRepo.save(product);
        System.out.println("SUCCESS: Product ID " + saved.getProductId());
        
        return saved;
    }

    public List<Product> getAllActiveProducts() {
        return productRepo.findAllActive();
    }

    public Optional<Product> getProductById(Integer id) {
        return productRepo.findById(id);
    }

    public Product updateProduct(Integer id, Product updated) {
        Product existing = productRepo.findById(id)
                .orElseThrow(() -> new RuntimeException("Product not found"));

        // Same validation as create...
        if (updated.getProductName() == null || updated.getProductName().trim().isEmpty()) {
            throw new RuntimeException("Product name is required");
        }
        existing.setProductName(updated.getProductName());
        existing.setDescription(updated.getDescription());
        existing.setPrice(updated.getPrice());
        existing.setStockQuantity(updated.getStockQuantity());
        existing.setImageUrl(updated.getImageUrl());

        // Category update (optional)
        if (updated.getCategory() != null && updated.getCategory().getCategoryId() != null) {
            Category cat = categoryRepo.findById(updated.getCategory().getCategoryId())
                    .orElseThrow(() -> new RuntimeException("Category not found"));
            existing.setCategory(cat);
        }

        return productRepo.save(existing);
    }

    public Product deactivateProduct(Integer id) {
        Product p = productRepo.findById(id)
                .orElseThrow(() -> new RuntimeException("Product not found"));
        p.setStatus(false);
        return productRepo.save(p);
    }
}