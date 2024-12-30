package com.leo.banking.customer;

import jakarta.persistence.*;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "customer")
public class Customer {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long customer_id;

    @NotNull
    @Size(min = 1, max = 50)
    private String firstName;

    @NotNull
    @Size(min = 1, max = 50)
    private String lastName;

    @Column(unique = true, nullable = false)
    @Email
    private String email;

    @NotNull
    @Size(min = 10, max = 15)
    @Pattern(regexp = "^[0-9]+$", message = "Phone number must be numeric")
    private String phoneNumber;

    @NotNull
    @Size(min = 5, max = 100)
    private String address;

    @NotNull
    @Pattern(regexp = "^[A-Z0-9]+$", message = "National ID must be alphanumeric")
    private String nationalId;

    @NotNull
    private LocalDate dateOfBirth;

    @NotNull
    @Enumerated(EnumType.STRING)
    private Gender gender;

    @Enumerated(EnumType.STRING)
    private MaritalStatus maritalStatus;

    @NotNull
    @Pattern(regexp = "^[A-Z]{2}[0-9]{12}$", message = "Invalid account number format")
    private String accountNumber;

    @Lob
    @Column(name = "kyc_documents", nullable = false)
    private byte[] kycDocuments; // Store KYC documents (e.g., ID scans) as binary data

    @Column(name = "created_at", updatable = false, nullable = false)
    private LocalDate createdAt = LocalDate.now();

    @Column(name = "updated_at", nullable = false)
    private LocalDate updatedAt = LocalDate.now();

    @PrePersist
    protected void onCreate() {
        createdAt = LocalDate.now();
        updatedAt = LocalDate.now();
    }

    @PreUpdate
    protected void onUpdate() {
        updatedAt = LocalDate.now();
    }

    public enum Gender {
        MALE, FEMALE, OTHER
    }

    public enum MaritalStatus {
        SINGLE, MARRIED, DIVORCED, WIDOWED
    }
}
