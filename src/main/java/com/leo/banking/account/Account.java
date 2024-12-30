package com.leo.banking.account;

import com.leo.banking.customer.Customer;  // Assuming you have a Customer entity
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;
import java.time.LocalDateTime;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Entity
public class Account {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;  // Account ID

    @Column(unique = true, nullable = false)
    private String accountNumber;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private AccountType accountType;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "customer_id", nullable = false)
    private Customer customer;  // The customer who owns the account (optional)

    @Column(nullable = false)
    private BigDecimal balance = BigDecimal.ZERO;  // Account balance (default to zero)

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private AccountStatus status;  // Account status (ACTIVE, CLOSED, etc.)

    @Column(nullable = false, updatable = false)
    private LocalDateTime createdAt;  // Timestamp when the account was created

    @Column(nullable = false)
    private LocalDateTime updatedAt;  // Timestamp for the last update of the account

    // Lifecycle callback to set createdAt and updatedAt timestamps
    @PrePersist
    public void onPrePersist() {
        LocalDateTime now = LocalDateTime.now();
        this.createdAt = now;
        this.updatedAt = now;
    }

    @PreUpdate
    public void onPreUpdate() {
        this.updatedAt = LocalDateTime.now();
    }
}
