package com.bezkoder.spring.jpa.postgresql.Service;

import java.util.List;
import java.util.Optional;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

import com.bezkoder.spring.jpa.postgresql.Request.LoginRequest;
import com.bezkoder.spring.jpa.postgresql.model.User;
import com.bezkoder.spring.jpa.postgresql.model.Role;
import com.bezkoder.spring.jpa.postgresql.model.AccountStatus;
import com.bezkoder.spring.jpa.postgresql.repository.UsersRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;

@Service
public class UserService {

    @Autowired
    UsersRepo usersRepo;

    private static final String SECRET_KEY = "mySecretKey";
    private static final long EXPIRATION_TIME = 86400000; // 1 day in ms

    public User addUser(User user) {
        // Set default role if not specified
        if (user.getRole() == null) {
            user.setRole(Role.USER);
        }
        // Set default status to PENDING
        user.setAccountStatus(AccountStatus.PENDING);
        return usersRepo.save(user);
    }

    public String loginUser(LoginRequest loginRequest) {
        Optional<User> userOpt = usersRepo.findAll().stream()
                .filter(u -> u.getEmail().equals(loginRequest.getEmail()))
                .findFirst();
        if (userOpt.isEmpty()) {
            return null;
        }
        User user = userOpt.get();

        // Check if account is accepted
        if (user.getAccountStatus() != AccountStatus.ACCEPTED) {
            return null;
        }

        if (!user.getPassword().equals(loginRequest.getPassword())) {
            return null;
        }

        // Generate JWT token with role and status
        String token = Jwts.builder()
                .setSubject(user.getEmail())
                .claim("name", user.getName())
                .claim("id", user.getId())
                .claim("role", user.getRole().name())
                .claim("status", user.getAccountStatus().name())
                .setIssuedAt(new Date())
                .setExpiration(new Date(System.currentTimeMillis() + EXPIRATION_TIME))
                .signWith(SignatureAlgorithm.HS256, SECRET_KEY)
                .compact();
        return token;
    }

    public List<User> getAllUsers() {
        return usersRepo.findAll();
    }

    public Optional<User> getUserById(Long id) {
        return usersRepo.findById(id);
    }

    public User updateUser(Long id, User userDetails) {
        Optional<User> userOpt = usersRepo.findById(id);
        if (userOpt.isEmpty()) {
            return null;
        }
        User user = userOpt.get();
        if (userDetails.getName() != null) {
            user.setName(userDetails.getName());
        }
        if (userDetails.getEmail() != null) {
            user.setEmail(userDetails.getEmail());
        }
        if (userDetails.getPassword() != null) {
            user.setPassword(userDetails.getPassword());
        }
        if (userDetails.getRole() != null) {
            user.setRole(userDetails.getRole());
        }
        if (userDetails.getLastName() != null) {
            user.setLastName(userDetails.getLastName());
        }
        if (userDetails.getAccountStatus() != null) {
            user.setAccountStatus(userDetails.getAccountStatus());
        }
        return usersRepo.save(user);
    }

    public boolean deleteUser(Long id) {
        Optional<User> userOpt = usersRepo.findById(id);
        if (userOpt.isEmpty()) {
            return false;
        }
        usersRepo.delete(userOpt.get());
        return true;
    }

    // New methods for account status management
    public User updateAccountStatus(Long id, AccountStatus newStatus) {
        Optional<User> userOpt = usersRepo.findById(id);
        if (userOpt.isEmpty()) {
            return null;
        }
        User user = userOpt.get();
        user.setAccountStatus(newStatus);
        return usersRepo.save(user);
    }

    public List<User> getUsersByStatus(AccountStatus status) {
        return usersRepo.findAll().stream()
                .filter(user -> user.getAccountStatus() == status)
                .toList();
    }

    public List<User> getPendingUsers() {
        return getUsersByStatus(AccountStatus.PENDING);
    }

    // New methods for role management
    public User updateUserRole(Long id, Role newRole) {
        Optional<User> userOpt = usersRepo.findById(id);
        if (userOpt.isEmpty()) {
            return null;
        }
        User user = userOpt.get();
        user.setRole(newRole);
        return usersRepo.save(user);
    }

    public List<User> getUsersByRole(Role role) {
        return usersRepo.findAll().stream()
                .filter(user -> user.getRole() == role)
                .toList();
    }

    public Map<String, String> logoutUser(String token) {
        Map<String, String> response = new HashMap<>();
        try {
            // In a real application, you might want to blacklist the token
            // For now, we'll just return a success message
            response.put("message", "Successfully logged out");
            return response;
        } catch (Exception e) {
            response.put("error", "Error during logout");
            return response;
        }
    }
}