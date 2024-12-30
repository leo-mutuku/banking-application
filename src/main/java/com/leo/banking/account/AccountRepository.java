package com.leo.banking.account;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface AccountRepository extends JpaRepository<Account, Long> {

    // Custom query method to find an Account by Account Number (if you want this functionality)
    Optional<Account> findByAccountNumber(String accountNumber);

    // No need to explicitly define methods like save, findById, findAll, etc.
    // These are automatically provided by JpaRepository

}
