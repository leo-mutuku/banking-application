package com.leo.banking.account;

public enum AccountStatus {
    ACTIVE,    // Account is active and operational
    CLOSED,    // Account is closed and no longer active
    SUSPENDED, // Account is temporarily suspended
    FROZEN,    // Account is frozen due to security or other reasons
    PENDING    // Account is in a pending state, awaiting verification or activation
}
