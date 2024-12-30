package com.leo.banking.otp;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface OTPRepository extends JpaRepository<OTP, Long> {

    // Find OTP by the user, with the option to check its expiration status
    Optional<OTP> findByUserIdAndExpiredFalse(Integer userId);

    // You could add a method to find expired OTPs if needed
    Optional<OTP> findByOtpAndExpiredFalse(String otp);

    // You can add more custom queries if needed, like fetching OTPs that are expired
    // Optional<OTP> findByExpiredTrue();
}
