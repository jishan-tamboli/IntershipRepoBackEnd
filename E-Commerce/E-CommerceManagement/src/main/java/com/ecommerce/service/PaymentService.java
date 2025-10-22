package com.ecommerce.service;

import com.ecommerce.entity.Payment;
import com.ecommerce.repository.PaymentRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import java.util.List;
import java.util.Optional;

@Service
@Transactional
public class PaymentService {
	@Autowired
	private PaymentRepository paymentRepo;

	public Payment createPayment(Payment payment) {
		if (payment.getOrderId() == null) {
			throw new RuntimeException("Order ID is required");
		}
		if (payment.getAmount() == null) {
			throw new RuntimeException("Amount is required");
		}
		payment.setPaymentStatus("Paid"); // Simulate success
		return paymentRepo.save(payment);
	}

	public List<Payment> getAllPayments() {
		return paymentRepo.findAll();
	}

	public Optional<Payment> getPaymentById(Integer id) {
		return paymentRepo.findById(id);
	}

	public Payment refundPayment(Integer id) {
		Payment p = paymentRepo.findById(id).orElseThrow(() -> new RuntimeException("Payment not found"));
		if ("Refunded".equals(p.getPaymentStatus())) {
			throw new RuntimeException("Already refunded");
		}
		p.setPaymentStatus("Refunded");
		return paymentRepo.save(p);
	}
}