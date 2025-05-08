package com.bezkoder.spring.jpa.postgresql.Service;

import java.util.List;
import java.util.Optional;
import java.util.Date;

import com.bezkoder.spring.jpa.postgresql.Request.LoginRequest;
import com.bezkoder.spring.jpa.postgresql.model.User;
import com.bezkoder.spring.jpa.postgresql.repository.UsersRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;

@Service
public class UserService {

    @Autowired
    UsersRepo usersRepo;

    private static final String SECRET_KEY = "mySecretKey";
    private static final long EXPIRATION_TIME = 86400000; // 1 day in ms
    private final BCryptPasswordEncoder passwordEncoder = new BCryptPasswordEncoder();

    private User findUserById(Long id) {
        return usersRepo.findById(id).orElse(null);
    }

    private User findUserByEmail(String email) {
        return usersRepo.findAll().stream()
                .filter(u -> u.getEmail().equals(email))
                .findFirst()
                .orElse(null);
    }

    /**
     * Adds a new user to the database.
     * 
     * @param user The user to add.
     * @return The saved user.
     */
    public User addUser(User user) {
        user.setPassword(passwordEncoder.encode(user.getPassword()));
        return usersRepo.save(user);
    }

    /**
     * Authenticates a user and generates a JWT token.
     * 
     * @param loginRequest The login request containing email and password.
     * @return The JWT token if authentication is successful, null otherwise.
     */
    public String loginUser(LoginRequest loginRequest) {
        User user = findUserByEmail(loginRequest.getEmail());
        if (user == null || !passwordEncoder.matches(loginRequest.getPassword(), user.getPassword())) {
            return null;
        }
        return generateToken(user);
    }

    private String generateToken(User user) {
        return Jwts.builder()
                .setSubject(user.getEmail())
                .claim("name", user.getName())
                .claim("id", user.getId())
                .setIssuedAt(new Date())
                .setExpiration(new Date(System.currentTimeMillis() + EXPIRATION_TIME))
                .signWith(SignatureAlgorithm.HS256, SECRET_KEY)
                .compact();
    }

    /**
     * Retrieves all users from the database.
     * 
     * @return A list of all users.
     */
    public List<User> getAllUsers() {
        return usersRepo.findAll();
    }

    /**
     * Retrieves a user by their ID.
     * 
     * @param id The ID of the user to retrieve.
     * @return An Optional containing the user if found, empty otherwise.
     */
    public Optional<User> getUserById(Long id) {
        return usersRepo.findById(id);
    }

    /**
     * Updates a user's details by their ID.
     * 
     * @param id          The ID of the user to update.
     * @param userDetails The new user details.
     * @return The updated user if found, null otherwise.
     */
    public User updateUser(Long id, User userDetails) {
        User user = findUserById(id);
        if (user == null) {
            return null;
        }
        updateUserFields(user, userDetails);
        return usersRepo.save(user);
    }

    private void updateUserFields(User user, User userDetails) {
        if (userDetails.getName() != null) {
            user.setName(userDetails.getName());
        }
        if (userDetails.getEmail() != null) {
            user.setEmail(userDetails.getEmail());
        }
        if (userDetails.getPassword() != null) {
            user.setPassword(passwordEncoder.encode(userDetails.getPassword()));
        }
        if (userDetails.getRole() != null) {
            user.setRole(userDetails.getRole());
        }
    }

    /**
     * Deletes a user by their ID.
     * 
     * @param id The ID of the user to delete.
     * @return true if the user was deleted, false otherwise.
     */
    public boolean deleteUser(Long id) {
        User user = findUserById(id);
        if (user == null) {
            return false;
        }
        usersRepo.delete(user);
        return true;
    }

}