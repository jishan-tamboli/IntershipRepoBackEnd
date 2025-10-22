package com.ecommerce.repository;

import java.util.List;

import com.ecommerce.entity.Product;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;


@Repository
public interface ProductRepository extends JpaRepository<Product, Integer> {

    @Query("SELECT p FROM Product p WHERE p.status = true")
    List<Product> findAllActive();

    @Query("SELECT COUNT(p) FROM Product p WHERE p.category.categoryId = :catId AND p.status = true")
    long countActiveByCategoryId(Integer catId);
}