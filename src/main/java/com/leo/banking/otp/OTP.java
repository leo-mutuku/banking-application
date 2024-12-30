package com.leo.banking.otp;

import com.leo.banking.user.User;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Entity
public class OTP {

    @Id
    @GeneratedValue
    private Long id;

    @Column(nullable = false, unique = true)
    private String otp;

    @Column(nullable = false)
    private boolean expired;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id", nullable = false)
    private User user;

    @Column(nullable = false, updatable = false)
    private LocalDateTime generatedAt;

    @Column(nullable = false)
    private LocalDateTime expiresAt;


    @PrePersist
    public void onPrePersist() {
        this.generatedAt = LocalDateTime.now();
        this.expiresAt = this.generatedAt.plusMinutes(5);
        this.expired = false;
    }
}
