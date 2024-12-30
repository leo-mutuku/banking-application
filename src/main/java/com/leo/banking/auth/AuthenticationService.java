package com.leo.banking.auth;

import com.leo.banking.config.JwtService;
import com.leo.banking.token.Token;
import com.leo.banking.token.TokenRepository;
import com.leo.banking.token.TokenType;
import com.leo.banking.user.User;
import com.leo.banking.user.UserRepository;
import com.fasterxml.jackson.databind.ObjectMapper;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpHeaders;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.IOException;

@Service
@RequiredArgsConstructor
public class AuthenticationService {
  private final UserRepository repository;
  private final TokenRepository tokenRepository;
  private final PasswordEncoder passwordEncoder;
  private final JwtService jwtService;
  private final AuthenticationManager authenticationManager;
  private static final Logger logger = LoggerFactory.getLogger(AuthenticationService.class); // Initialize the logger


  public AuthenticationResponse register(RegisterRequest request) {
    try {
      // Attempt to create and save the user
      var user = User.builder()
              .firstname(request.getFirstname())
              .lastname(request.getLastname())
              .email(request.getEmail())
              .password(passwordEncoder.encode(request.getPassword())) // Encoding password
              .phone(request.getPhone())
              .role(request.getRole())
              .build();

      var savedUser = repository.save(user); // Save the user in the repository
      var jwtToken = jwtService.generateToken(user); // Generate JWT token
      var refreshToken = jwtService.generateRefreshToken(user); // Generate refresh token
      saveUserToken(savedUser, jwtToken); // Save token in the database

      // Return the response with access and refresh tokens
      return AuthenticationResponse.builder()
              .accessToken(jwtToken)
              .refreshToken(refreshToken)
              .build();

    } catch (Exception e) {

      System.err.println("Error during registration: " + e.getMessage());


      throw new RuntimeException("Registration failed. Please try again later.");

    }
  }

  public AuthenticationResponse authenticate(AuthenticationRequest request) {
    try {
      // Attempt to authenticate the user using the authentication manager
      authenticationManager.authenticate(
              new UsernamePasswordAuthenticationToken(
                      request.getEmail(),
                      request.getPassword()
              )
      );

      // Fetch the user from the repository based on the email
      var user = repository.findByEmail(request.getEmail())
              .orElseThrow(() -> new RuntimeException("User not found with email: " + request.getEmail()));

      // Generate JWT and refresh token for the authenticated user
      var jwtToken = jwtService.generateToken(user);
      var refreshToken = jwtService.generateRefreshToken(user);

      // Revoke any old tokens and save the new token
      revokeAllUserTokens(user);
      saveUserToken(user, jwtToken);

      return AuthenticationResponse.builder()
              .accessToken(jwtToken)
              .refreshToken(refreshToken)
              .build();

    } catch (Exception e) {
      System.err.println("Error during authentication: " + e.getMessage());
      throw new RuntimeException("Authentication failed. Please try again later."); // Custom exception message
    }
  }

  private void saveUserToken(User user, String jwtToken) {
    var token = Token.builder()
        .user(user)
        .token(jwtToken)
        .tokenType(TokenType.BEARER)
        .expired(false)
        .revoked(false)
        .build();
    tokenRepository.save(token);
  }

  private void revokeAllUserTokens(User user) {
    var validUserTokens = tokenRepository.findAllValidTokenByUser(user.getId());
    if (validUserTokens.isEmpty())
      return;
    validUserTokens.forEach(token -> {
      token.setExpired(true);
      token.setRevoked(true);
    });
    tokenRepository.saveAll(validUserTokens);
  }

  public void refreshToken(
          HttpServletRequest request,
          HttpServletResponse response
  ) throws IOException {
    final String authHeader = request.getHeader(HttpHeaders.AUTHORIZATION);
    final String refreshToken;
    final String userEmail;
    if (authHeader == null ||!authHeader.startsWith("Bearer ")) {
      return;
    }
    refreshToken = authHeader.substring(7);
    userEmail = jwtService.extractUsername(refreshToken);
    if (userEmail != null) {
      var user = this.repository.findByEmail(userEmail)
              .orElseThrow();
      if (jwtService.isTokenValid(refreshToken, user)) {
        var accessToken = jwtService.generateToken(user);
        revokeAllUserTokens(user);
        saveUserToken(user, accessToken);
        var authResponse = AuthenticationResponse.builder()
                .accessToken(accessToken)
                .refreshToken(refreshToken)
                .build();
        new ObjectMapper().writeValue(response.getOutputStream(), authResponse);
      }
    }
  }
}
