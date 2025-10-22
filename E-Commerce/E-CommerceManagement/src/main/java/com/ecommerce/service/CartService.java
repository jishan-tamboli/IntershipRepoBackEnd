package com.ecommerce.service;

import com.ecommerce.entity.Cart;
import com.ecommerce.entity.Product;
import com.ecommerce.repository.CartRepository;
import com.ecommerce.repository.ProductRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import java.math.BigDecimal;
import java.util.List;
import java.util.Optional;

@Service
@Transactional
public class CartService {
    @Autowired private CartRepository cartRepo;
    @Autowired private ProductRepository productRepo;

    public Cart addToCart(Cart cart) {
        if (cart.getCustomerId() == null || cart.getProductId() == null || cart.getQuantity() == null || cart.getQuantity() <= 0) {
            throw new RuntimeException("Invalid cart details");
        }
        Product product = productRepo.findById(cart.getProductId())
                .orElseThrow(() -> new RuntimeException("Product not found"));
        if (product.getStockQuantity() < cart.getQuantity()) {
            throw new RuntimeException("Insufficient stock");
        }
        cart.setTotalPrice(product.getPrice().multiply(BigDecimal.valueOf(cart.getQuantity())));
        return cartRepo.save(cart);
    }

    public List<Cart> getCartByCustomer(Integer customerId) {
        return cartRepo.findByCustomerId(customerId);
    }

    public Cart updateCart(Integer id, Cart updated) {
        Cart existing = cartRepo.findById(id)
                .orElseThrow(() -> new RuntimeException("Cart item not found"));
        if (updated.getQuantity() != null && updated.getQuantity() > 0) {
            Product product = productRepo.findById(existing.getProductId())
                    .orElseThrow(() -> new RuntimeException("Product not found"));
            if (product.getStockQuantity() < updated.getQuantity()) {
                throw new RuntimeException("Insufficient stock");
            }
            existing.setQuantity(updated.getQuantity());
            existing.setTotalPrice(product.getPrice().multiply(BigDecimal.valueOf(updated.getQuantity())));
        }
        return cartRepo.save(existing);
    }

    public void removeFromCart(Integer id) {
        cartRepo.deleteById(id);
    }
}