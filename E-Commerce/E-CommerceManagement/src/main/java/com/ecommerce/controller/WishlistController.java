package com.ecommerce.controller;

import com.ecommerce.entity.Wishlist;
import com.ecommerce.service.WishlistService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/wishlists")
@CrossOrigin(origins = "http://localhost:5173")
public class WishlistController {
    @Autowired private WishlistService wishlistService;

    @PostMapping
    public ResponseEntity<Wishlist> addToWishlist(@RequestBody Wishlist wishlist) {
        try {
            return ResponseEntity.ok(wishlistService.addToWishlist(wishlist));
        } catch (RuntimeException e) {
            return ResponseEntity.badRequest().build();
        }
    }

    @GetMapping("/customer/{customerId}")
    public ResponseEntity<List<Wishlist>> getByCustomer(@PathVariable Integer customerId) {
        return ResponseEntity.ok(wishlistService.getWishlistByCustomer(customerId));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> remove(@PathVariable Integer id) {
        wishlistService.removeFromWishlist(id);
        return ResponseEntity.ok().build();
    }
}