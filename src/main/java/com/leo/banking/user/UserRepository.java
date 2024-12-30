package com.leo.banking.user;

import com.leo.banking.otp.OTP;  // Assuming OTP is in the otp package
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface UserRepository extends JpaRepository<User, Integer> {


  Optional<User> findByEmail(String email);


  Optional<OTP> findOTPByUserIdAndExpiredFalse(Integer userId);


  Optional<User> findById(Integer id);
}
