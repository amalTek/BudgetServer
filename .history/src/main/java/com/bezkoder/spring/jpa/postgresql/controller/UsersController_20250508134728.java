package com.bezkoder.spring.jpa.postgresql.controller;

import com.bezkoder.spring.jpa.postgresql.Request.LoginRequest;
import com.bezkoder.spring.jpa.postgresql.Service.UserService;
import com.bezkoder.spring.jpa.postgresql.model.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
import java.util.List;
import java.util.Optional;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;

@Tag(name = "User Management", description = "APIs for managing users")
@CrossOrigin()
@RestController
@RequestMapping("/api")
public class UsersController {

    @Autowired
    UserService userService;

    @Operation(summary = "Add a new user", description = "Creates a new user with the provided details.")
    @PostMapping("/addUser")
    public User addUser(@RequestBody User user) {
        return userService.addUser(user);
    }

    @Operation(summary = "Login user", description = "Authenticates a user and returns a JWT token.")
    @PostMapping("/loginUser")
    public ResponseEntity<?> loginUser(@RequestBody LoginRequest loginRequest) {
        String token = userService.loginUser(loginRequest);
        if (token == null) {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("Invalid credentials");
        }
        return ResponseEntity.ok(token);
    }

    @Operation(summary = "Get all users", description = "Retrieves a list of all users.")
    @GetMapping("/users")
    public List<User> getAllUsers() {
        return userService.getAllUsers();
    }

    @Operation(summary = "Get user by ID", description = "Retrieves a user by their ID.")
    @GetMapping("/users/{id}")
    public ResponseEntity<User> getUserById(@PathVariable Long id) {
        Optional<User> user = userService.getUserById(id);
        return user.map(ResponseEntity::ok).orElseGet(() -> ResponseEntity.notFound().build());
    }

    @Operation(summary = "Update user", description = "Updates a user's details by their ID.")
    @PutMapping("/users/{id}")
    public ResponseEntity<User> updateUser(@PathVariable Long id, @RequestBody User userDetails) {
        User updatedUser = userService.updateUser(id, userDetails);
        if (updatedUser == null) {
            return ResponseEntity.notFound().build();
        }
        return ResponseEntity.ok(updatedUser);
    }

    @Operation(summary = "Delete user", description = "Deletes a user by their ID.")
    @DeleteMapping("/users/{id}")
    public ResponseEntity<?> deleteUser(@PathVariable Long id) {
        boolean deleted = userService.deleteUser(id);
        if (!deleted) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("User not found");
        }
        return ResponseEntity.ok("User deleted successfully");
    }
}