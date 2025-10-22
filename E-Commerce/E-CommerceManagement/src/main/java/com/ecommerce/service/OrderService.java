package com.ecommerce.service;

import com.ecommerce.entity.Order;
import com.ecommerce.repository.OrderRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import java.util.List;
import java.util.Optional;

@Service
@Transactional
public class OrderService {
    @Autowired private OrderRepository orderRepo;

    public Order createOrder(Order order) {
        if (order.getCustomerId() == null) {
            throw new RuntimeException("Customer ID is required");
        }
        if (order.getTotalAmount() == null) {
            throw new RuntimeException("Total amount is required");
        }
        order.setStatus(true);
        return orderRepo.save(order);
    }

    public List<Order> getAllActiveOrders() {
        return orderRepo.findAllActive();
    }

    public Optional<Order> getOrderById(Integer id) {
        return orderRepo.findById(id);
    }

    public Order updateOrder(Integer id, Order updated) {
        Order existing = orderRepo.findById(id)
                .orElseThrow(() -> new RuntimeException("Order not found"));
        if (updated.getOrderStatus() != null) {
            existing.setOrderStatus(updated.getOrderStatus());
        }
        if (updated.getShippingAddress() != null) {
            existing.setShippingAddress(updated.getShippingAddress());
        }
        return orderRepo.save(existing);
    }

    public Order cancelOrder(Integer id) {
        Order o = orderRepo.findById(id)
                .orElseThrow(() -> new RuntimeException("Order not found"));
        if ("Shipped".equals(o.getOrderStatus()) || "Delivered".equals(o.getOrderStatus())) {
            throw new RuntimeException("Cannot cancel shipped or delivered order");
        }
        o.setOrderStatus("Cancelled");
        o.setStatus(false);
        return orderRepo.save(o);
    }
}