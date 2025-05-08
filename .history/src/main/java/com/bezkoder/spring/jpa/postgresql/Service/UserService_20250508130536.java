package com.bezkoder.spring.jpa.postgresql.Service;

import java.util.Optional;
import java.util.Date;

import com.bezkoder.spring.jpa.postgresql.Request.LoginRequest;
import com.bezkoder.spring.jpa.postgresql.model.User;
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
        if (!user.getPassword().equals(loginRequest.getPassword())) {
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

}