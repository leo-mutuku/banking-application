package com.leo.banking.customer;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface CustomerRepository extends JpaRepository<Customer, Long> {

    // Custom query method to find a customer by email
    Optional<Customer> findByEmail(String email);

    // Custom query method to find a customer by national ID
    Optional<Customer> findByNationalId(String nationalId);

    // Custom query method to find a customer by phone number
    Optional<Customer> findByPhoneNumber(String phoneNumber);
}
