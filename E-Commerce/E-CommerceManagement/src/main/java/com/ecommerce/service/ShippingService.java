package com.ecommerce.service;

import com.ecommerce.entity.Shipping;
import com.ecommerce.repository.ShippingRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import java.util.List;
import java.util.Optional;

@Service
@Transactional
public class ShippingService {
	@Autowired
	private ShippingRepository shippingRepo;

	public Shipping createShipping(Shipping shipping) {
		if (shipping.getOrderId() == null) {
			throw new RuntimeException("Order ID is required");
		}
		// Simulate cost calculation (e.g., based on weight/location; customize)
		if (shipping.getShippingCost() == null) {
			shipping.setShippingCost(shipping.getShippingCost()); // Placeholder
		}
		return shippingRepo.save(shipping);
	}

	public List<Shipping> getAllShipping() {
		return shippingRepo.findAll();
	}

	public Optional<Shipping> getShippingById(Integer id) {
		return shippingRepo.findById(id);
	}

	public Shipping updateShipping(Integer id, Shipping updated) {
		Shipping existing = shippingRepo.findById(id).orElseThrow(() -> new RuntimeException("Shipping not found"));
		if (updated.getCourierService() != null)
			existing.setCourierService(updated.getCourierService());
		if (updated.getTrackingNumber() != null)
			existing.setTrackingNumber(updated.getTrackingNumber());
		if (updated.getShippingStatus() != null)
			existing.setShippingStatus(updated.getShippingStatus());
		return shippingRepo.save(existing);
	}
}