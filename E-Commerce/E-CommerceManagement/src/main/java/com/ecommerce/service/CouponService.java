package com.ecommerce.service;

import com.ecommerce.entity.Coupon;
import com.ecommerce.repository.CouponRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import java.util.List;
import java.util.Optional;

@Service
@Transactional
public class CouponService {
	@Autowired
	private CouponRepository couponRepo;

	public Coupon createCoupon(Coupon coupon) {
		if (coupon.getCouponCode() == null || coupon.getDiscountType() == null || coupon.getDiscountValue() == null) {
			throw new RuntimeException("Invalid coupon details");
		}
		if (!"Percentage".equals(coupon.getDiscountType()) && !"Fixed".equals(coupon.getDiscountType())) {
			throw new RuntimeException("Invalid discount type");
		}
		coupon.setStatus(true);
		return couponRepo.save(coupon);
	}

	public List<Coupon> getAllActiveCoupons() {
		return couponRepo.findAllActive();
	}

	public Optional<Coupon> getCouponById(Integer id) {
		return couponRepo.findById(id);
	}

	public Coupon updateCoupon(Integer id, Coupon updated) {
		Coupon existing = couponRepo.findById(id).orElseThrow(() -> new RuntimeException("Coupon not found"));
		if (updated.getCouponCode() != null)
			existing.setCouponCode(updated.getCouponCode());
		if (updated.getDiscountType() != null)
			existing.setDiscountType(updated.getDiscountType());
		if (updated.getDiscountValue() != null)
			existing.setDiscountValue(updated.getDiscountValue());
		if (updated.getValidFrom() != null)
			existing.setValidFrom(updated.getValidFrom());
		if (updated.getValidTo() != null)
			existing.setValidTo(updated.getValidTo());
		if (updated.getUsageLimit() != null)
			existing.setUsageLimit(updated.getUsageLimit());
		return couponRepo.save(existing);
	}

	public Coupon deactivateCoupon(Integer id) {
		Coupon c = couponRepo.findById(id).orElseThrow(() -> new RuntimeException("Coupon not found"));
		c.setStatus(false);
		return couponRepo.save(c);
	}

	public void deleteCoupon(Integer id) {
		couponRepo.deleteById(id);
	}
}