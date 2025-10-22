package com.ecommerce.controller;

import com.ecommerce.entity.Coupon;
import com.ecommerce.service.CouponService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/coupons")
@CrossOrigin(origins = "http://localhost:5173")
public class CouponController {
    @Autowired private CouponService couponService;

    @PostMapping
    public ResponseEntity<Coupon> create(@RequestBody Coupon coupon) {
        try {
            return ResponseEntity.ok(couponService.createCoupon(coupon));
        } catch (RuntimeException e) {
            return ResponseEntity.badRequest().build();
        }
    }

    @GetMapping("/active")
    public ResponseEntity<List<Coupon>> getActive() {
        return ResponseEntity.ok(couponService.getAllActiveCoupons());
    }

    @GetMapping("/{id}")
    public ResponseEntity<Coupon> getById(@PathVariable Integer id) {
        return couponService.getCouponById(id)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    @PutMapping("/{id}")
    public ResponseEntity<Coupon> update(@PathVariable Integer id, @RequestBody Coupon coupon) {
        try {
            return ResponseEntity.ok(couponService.updateCoupon(id, coupon));
        } catch (RuntimeException e) {
            return ResponseEntity.badRequest().build();
        }
    }

    @PatchMapping("/{id}/deactivate")
    public ResponseEntity<Coupon> deactivate(@PathVariable Integer id) {
        try {
            return ResponseEntity.ok(couponService.deactivateCoupon(id));
        } catch (RuntimeException e) {
            return ResponseEntity.badRequest().build();
        }
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> delete(@PathVariable Integer id) {
        couponService.deleteCoupon(id);
        return ResponseEntity.ok().build();
    }
}