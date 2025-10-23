package com.ecommerce.service;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.ecommerce.entity.Category;
import com.ecommerce.repository.CategoryRepository;

@Service
@Transactional
public class CategoryService {

    @Autowired
    private CategoryRepository categoryRepository;

    // Create (unchanged)
    public Category createCategory(Category category) {
        if (categoryRepository.findByCategoryName(category.getCategoryName()).isPresent()) {
            throw new RuntimeException("Category name already exists");
        }
        if (category.getCategoryName() == null || category.getCategoryName().trim().isEmpty()) {
            throw new RuntimeException("Category name is required");
        }
        category.setStatus(true);
        category.setCreatedAt(LocalDateTime.now());
        return categoryRepository.save(category);
    }

    // Dashboard: Get all active (unchanged)
    public List<Category> getAllActiveCategories() {
        return categoryRepository.findAllActiveCategories();
    }

    // Get by ID (unchanged)
    public Optional<Category> getCategoryById(Integer id) {
        if (id == null || id <= 0) {
            return Optional.empty();
        }
        return categoryRepository.findById(id);
    }

    // Update (unchanged)
    public Category updateCategory(Integer id, Category updatedCategory) {
        Optional<Category> existing = categoryRepository.findById(id);
        if (existing.isPresent()) {
            Category category = existing.get();
            if (updatedCategory.getCategoryName() != null) {
                if (!updatedCategory.getCategoryName().equals(category.getCategoryName()) &&
                    categoryRepository.findByCategoryName(updatedCategory.getCategoryName()).isPresent()) {
                    throw new RuntimeException("Category name already exists");
                }
                category.setCategoryName(updatedCategory.getCategoryName());
            }
            if (updatedCategory.getDescription() != null) {
                category.setDescription(updatedCategory.getDescription());
            }
            category.setUpdatedAt(LocalDateTime.now());
            return categoryRepository.save(category);
        }
        throw new RuntimeException("Category not found with ID: " + id);
    }


    public Category deactivateCategory(Integer id) {
        Optional<Category> existing = categoryRepository.findById(id);
        if (existing.isPresent()) {
            Category category = existing.get();
            boolean hasProducts = false; 
            if (hasProducts) {
                throw new RuntimeException("Cannot deactivate: Assign products to a new category first");
            }
            
            category.setStatus(false);
            category.setUpdatedAt(LocalDateTime.now());
            return categoryRepository.save(category);
        }
        throw new RuntimeException("Category not found with ID: " + id);
    }
}