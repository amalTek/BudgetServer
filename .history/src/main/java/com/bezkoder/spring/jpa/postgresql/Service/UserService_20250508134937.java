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
        Optional<User> userOpt = usersRepo.findAll().stream()
                .filter(u -> u.getEmail().equals(loginRequest.getEmail()))
                .findFirst();
        if (userOpt.isEmpty()) {
            return null;
        }
        User user = userOpt.get();
        if (!passwordEncoder.matches(loginRequest.getPassword(), user.getPassword())) {
            return null;
        }
        // Generate JWT token
        String token = Jwts.builder()
                .setSubject(user.getEmail())
                .claim("name", user.getName())
                .claim("id", user.getId())
                .setIssuedAt(new Date())
                .setExpiration(new Date(System.currentTimeMillis() + EXPIRATION_TIME))
                .signWith(SignatureAlgorithm.HS256, SECRET_KEY)
                .compact();
        return token;
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
            user.setPassword(passwordEncoder.encode(userDetails.getPassword()));
        }
        if (userDetails.getRole() != null) {
            user.setRole(userDetails.getRole());
        }
        return usersRepo.save(user);
    }

    /**
     * Deletes a user by their ID.
     * 
     * @param id The ID of the user to delete.
     * @return true if the user was deleted, false otherwise.
     */
    public boolean deleteUser(Long id) {
        Optional<User> userOpt = usersRepo.findById(id);
        if (userOpt.isEmpty()) {
            return false;
        }
        usersRepo.delete(userOpt.get());
        return true;
    }

}