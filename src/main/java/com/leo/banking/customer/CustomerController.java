package com.leo.banking.customer;

import com.leo.banking.user.Role;
import com.leo.banking.user.User;
import com.leo.banking.user.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/v1/customers")
public class CustomerController {

    private final CustomerService customerService;
    private final UserService userService;

    @Autowired
    public CustomerController(CustomerService customerService, UserService userService) {
        this.customerService = customerService;
        this.userService = userService;
    }

    // Get current customer's details
    @GetMapping("/me")
    public ResponseEntity<Customer> getMyDetails() {
        // Get the currently authenticated user
        User user = userService.getAuthenticatedUser();

        // Check if the authenticated user has a customer object associated with them
        if (user.getCustomer() == null) {
            return ResponseEntity.status(403).build(); // Forbidden if the user is not a customer
        }

        // Return the customer details
        return ResponseEntity.ok(user.getCustomer());
    }

    // Get all customers (Admin only)
    @GetMapping
    public ResponseEntity<List<Customer>> getAllCustomers() {
        // Get the currently authenticated user
        System.out.println("getAllCustomers");
        User user = userService.getAuthenticatedUser();
        System.out.println(user.getCustomer());

        // Check if the user has ADMIN role
        if (!userService.hasRole(Role.ADMIN)) {
            return ResponseEntity.status(403).build(); // Forbidden for non-admin users
        }

        // Return the list of all customers
        List<Customer> customers = customerService.getAllCustomers();
        return ResponseEntity.ok(customers);
    }
}
