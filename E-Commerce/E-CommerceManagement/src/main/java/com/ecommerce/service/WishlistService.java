package com.ecommerce.service;

import com.ecommerce.entity.Wishlist;
import com.ecommerce.repository.WishlistRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import java.util.List;
import java.util.Optional;

@Service
@Transactional
public class WishlistService {
    @Autowired private WishlistRepository wishlistRepo;

    public Wishlist addToWishlist(Wishlist wishlist) {
        if (wishlist.getCustomerId() == null || wishlist.getProductId() == null) {
            throw new RuntimeException("Invalid wishlist details");
        }
        // Check for duplicates if needed
        return wishlistRepo.save(wishlist);
    }

    public List<Wishlist> getWishlistByCustomer(Integer customerId) {
        return wishlistRepo.findByCustomerId(customerId);
    }

    public void removeFromWishlist(Integer id) {
        wishlistRepo.deleteById(id);
    }
}