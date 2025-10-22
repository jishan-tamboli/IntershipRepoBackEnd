package com.ecommerce.controller;

import com.ecommerce.entity.Shipping;
import com.ecommerce.service.ShippingService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/shipping")
@CrossOrigin(origins = "http://localhost:5173")
public class ShippingController {
    @Autowired private ShippingService shippingService;

    @PostMapping
    public ResponseEntity<Shipping> create(@RequestBody Shipping shipping) {
        try {
            return ResponseEntity.ok(shippingService.createShipping(shipping));
        } catch (RuntimeException e) {
            return ResponseEntity.badRequest().build();
        }
    }

    @GetMapping
    public ResponseEntity<List<Shipping>> getAll() {
        return ResponseEntity.ok(shippingService.getAllShipping());
    }

    @GetMapping("/{id}")
    public ResponseEntity<Shipping> getById(@PathVariable Integer id) {
        return shippingService.getShippingById(id)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    @PutMapping("/{id}")
    public ResponseEntity<Shipping> update(@PathVariable Integer id, @RequestBody Shipping shipping) {
        try {
            return ResponseEntity.ok(shippingService.updateShipping(id, shipping));
        } catch (RuntimeException e) {
            return ResponseEntity.badRequest().build();
        }
    }
}