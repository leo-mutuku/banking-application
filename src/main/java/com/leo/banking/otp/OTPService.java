package com.leo.banking.otp;

import com.leo.banking.user.User;
import com.leo.banking.user.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.security.SecureRandom;
import java.time.LocalDateTime;
import java.util.Optional;

@Service
public class OTPService {

    private final OTPRepository otpRepository;
    private final UserRepository userRepository;

    private static final int OTP_EXPIRATION_TIME_MINUTES = 5;
    private static final int OTP_LENGTH = 6;

    @Autowired
    public OTPService(OTPRepository otpRepository, UserRepository userRepository) {
        this.otpRepository = otpRepository;
        this.userRepository = userRepository;
    }

    // 1. Generate OTP for a user
    @Transactional
    public OTP generateOTP(Integer userId) {
        Optional<User> userOptional = userRepository.findById(userId);

        if (userOptional.isEmpty()) {
            throw new RuntimeException("User not found with ID: " + userId);
        }

        User user = userOptional.get();
        String otp = generateRandomOTP();

        OTP otpEntity = OTP.builder()
                .user(user)
                .otp(otp)
                .expired(false)
                .generatedAt(LocalDateTime.now())
                .expiresAt(LocalDateTime.now().plusMinutes(OTP_EXPIRATION_TIME_MINUTES))
                .build();

        return otpRepository.save(otpEntity);
    }

    // 2. Validate OTP
    public boolean validateOTP(Integer userId, String otp) {
        Optional<OTP> otpOptional = otpRepository.findByUserIdAndExpiredFalse(userId);

        if (otpOptional.isPresent()) {
            OTP storedOtp = otpOptional.get();
            if (storedOtp.getOtp().equals(otp) && !storedOtp.isExpired()) {
                storedOtp.setExpired(true);
                otpRepository.save(storedOtp);
                return true;
            }
        }
        return false;
    }


    private String generateRandomOTP() {
        SecureRandom random = new SecureRandom();
        StringBuilder otp = new StringBuilder();

        for (int i = 0; i < OTP_LENGTH; i++) {
            otp.append(random.nextInt(10));
        }

        return otp.toString();
    }
}
