package com.ecommerce.repository;

import com.ecommerce.entity.Wishlist;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import java.util.List;

public interface WishlistRepository extends JpaRepository<Wishlist, Integer> {
    @Query("SELECT w FROM Wishlist w WHERE w.customerId = :customerId")
    List<Wishlist> findByCustomerId(Integer customerId);
}