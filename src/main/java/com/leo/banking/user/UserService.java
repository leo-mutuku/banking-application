package com.leo.banking.user;

import lombok.RequiredArgsConstructor;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.security.Principal;
import java.util.Collection;

@Service
@RequiredArgsConstructor
public class UserService {

    private final PasswordEncoder passwordEncoder;
    private final UserRepository repository;

    // Method to check if the authenticated user has a specific role
    public boolean hasRole(Role role) {
        // Get the currently authenticated user
        User user = getAuthenticatedUser();

        // Check if the user has the specific role
        return user.getAuthorities().stream()
                .anyMatch(grantedAuthority -> grantedAuthority.getAuthority().equals(role.name()));
    }

    // Method to get the authenticated user
    public User getAuthenticatedUser() {
        // Get the current authentication object from the security context
        UsernamePasswordAuthenticationToken authenticationToken =
                (UsernamePasswordAuthenticationToken) SecurityContextHolder.getContext().getAuthentication();

        // Return the user object
        return (User) authenticationToken.getPrincipal();
    }

    // Change password logic for a user
    public void changePassword(ChangePasswordRequest request, Principal connectedUser) {

        // Get the current authenticated user from the connectedUser principal
        var user = (User) ((UsernamePasswordAuthenticationToken) connectedUser).getPrincipal();

        // Check if the current password matches the stored password
        if (!passwordEncoder.matches(request.getCurrentPassword(), user.getPassword())) {
            throw new IllegalStateException("Wrong password");
        }

        // Check if the new passwords match
        if (!request.getNewPassword().equals(request.getConfirmationPassword())) {
            throw new IllegalStateException("Passwords are not the same");
        }

        // Update the user's password
        user.setPassword(passwordEncoder.encode(request.getNewPassword()));

        // Save the updated user object with the new password
        repository.save(user);
    }
}
