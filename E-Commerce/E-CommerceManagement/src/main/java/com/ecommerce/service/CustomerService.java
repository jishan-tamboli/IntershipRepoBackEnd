package com.ecommerce.service;

import com.ecommerce.entity.Customer;
import com.ecommerce.repository.CustomerRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import java.util.List;
import java.util.Optional;

@Service
@Transactional
public class CustomerService {
    @Autowired private CustomerRepository customerRepo;

    public Customer createCustomer(Customer customer) {
        if (customer.getEmail() == null || customer.getEmail().trim().isEmpty()) {
            throw new RuntimeException("Email is required");
        }
        if (customerRepo.findByEmail(customer.getEmail()).isPresent()) {
            throw new RuntimeException("Email already exists");
        }
        customer.setStatus(true);
        return customerRepo.save(customer);
    }

    public List<Customer> getAllActiveCustomers() {
        return customerRepo.findAllActive();
    }

    public Optional<Customer> getCustomerById(Integer id) {
        return customerRepo.findById(id);
    }

    public Customer updateCustomer(Integer id, Customer updated) {
        Customer existing = customerRepo.findById(id)
                .orElseThrow(() -> new RuntimeException("Customer not found"));
        if (updated.getFirstName() != null) existing.setFirstName(updated.getFirstName());
        if (updated.getLastName() != null) existing.setLastName(updated.getLastName());
        if (updated.getEmail() != null) {
            if (!updated.getEmail().equals(existing.getEmail()) && customerRepo.findByEmail(updated.getEmail()).isPresent()) {
                throw new RuntimeException("Email already exists");
            }
            existing.setEmail(updated.getEmail());
        }
        if (updated.getPhone() != null) existing.setPhone(updated.getPhone());
        return customerRepo.save(existing);
    }

    public Customer deactivateCustomer(Integer id) {
        Customer c = customerRepo.findById(id)
                .orElseThrow(() -> new RuntimeException("Customer not found"));
        c.setStatus(false);
        return customerRepo.save(c);
    }
}