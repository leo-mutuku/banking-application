package com.leo.banking.account;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import lombok.RequiredArgsConstructor;

import java.math.BigDecimal;
import java.util.List;

@Service
@RequiredArgsConstructor
public class AccountService {

    private final AccountRepository accountRepository;

    // 1. Get Account Balance
    public BigDecimal getAccountBalance(Long accountId) {
        Account account = accountRepository.findById(accountId)
                .orElseThrow(() -> new AccountNotFoundException(""+accountId ));
        return account.getBalance();
    }

    // 2. Register a New Account
    @Transactional
    public Account registerAccount(Account account) {
        return accountRepository.save(account);
    }

    // 3. Withdraw from Account
    @Transactional
    public Account withdraw(Long accountId, BigDecimal amount) {
        Account account = accountRepository.findById(accountId)
                .orElseThrow(() -> new AccountNotFoundException("Account not found with ID: " + accountId));

        BigDecimal currentBalance = account.getBalance();
        if (currentBalance.compareTo(amount) >= 0) {
            account.setBalance(currentBalance.subtract(amount));
            account.setUpdatedAt(java.time.LocalDateTime.now());
            return accountRepository.save(account);
        } else {
            throw new InsufficientFundsException("Insufficient funds for withdrawal");
        }
    }

    // 4. Get All Accounts
    public List<Account> getAllAccounts() {
        return accountRepository.findAll();
    }
}
