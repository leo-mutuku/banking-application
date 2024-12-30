package com.leo.banking.customer;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class CustomerService {

    private final CustomerRepository customerRepository;

    @Autowired
    public CustomerService(CustomerRepository customerRepository) {
        this.customerRepository = customerRepository;
    }

    // Save or update a customer
    public Customer saveCustomer(Customer customer) {
        return customerRepository.save(customer);
    }

    // Retrieve a customer by their ID
    public Optional<Customer> getCustomerById(Long customerId) {
        return customerRepository.findById(customerId);
    }

    // Retrieve a customer by their email
    public Optional<Customer> getCustomerByEmail(String email) {
        return customerRepository.findByEmail(email);
    }

    // Retrieve a customer by their national ID
    public Optional<Customer> getCustomerByNationalId(String nationalId) {
        return customerRepository.findByNationalId(nationalId);
    }

    // Retrieve a customer by their phone number
    public Optional<Customer> getCustomerByPhoneNumber(String phoneNumber) {
        return customerRepository.findByPhoneNumber(phoneNumber);
    }

    // Retrieve all customers
    public List<Customer> getAllCustomers() {
        return customerRepository.findAll();
    }

    // Delete a customer by their ID
    public void deleteCustomer(Long customerId) {
        if (customerRepository.existsById(customerId)) {
            customerRepository.deleteById(customerId);
        } else {
            throw new IllegalArgumentException("Customer with ID " + customerId + " does not exist.");
        }
    }

    // Check if a customer exists by their email
    public boolean customerExistsByEmail(String email) {
        return customerRepository.findByEmail(email).isPresent();
    }

    // Check if a customer exists by their national ID
    public boolean customerExistsByNationalId(String nationalId) {
        return customerRepository.findByNationalId(nationalId).isPresent();
    }
}
