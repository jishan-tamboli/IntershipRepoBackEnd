package com.ecommerce.controller;

import com.ecommerce.entity.Cart;
import com.ecommerce.service.CartService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/carts")
@CrossOrigin(origins = "http://localhost:5173")
public class CartController {
    @Autowired private CartService cartService;

    @PostMapping
    public ResponseEntity<Cart> addToCart(@RequestBody Cart cart) {
        try {
            return ResponseEntity.ok(cartService.addToCart(cart));
        } catch (RuntimeException e) {
            return ResponseEntity.badRequest().build();
        }
    }

    @GetMapping("/customer/{customerId}")
    public ResponseEntity<List<Cart>> getByCustomer(@PathVariable Integer customerId) {
        return ResponseEntity.ok(cartService.getCartByCustomer(customerId));
    }

    @PutMapping("/{id}")
    public ResponseEntity<Cart> update(@PathVariable Integer id, @RequestBody Cart cart) {
        try {
            return ResponseEntity.ok(cartService.updateCart(id, cart));
        } catch (RuntimeException e) {
            return ResponseEntity.badRequest().build();
        }
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> remove(@PathVariable Integer id) {
        cartService.removeFromCart(id);
        return ResponseEntity.ok().build();
    }
}