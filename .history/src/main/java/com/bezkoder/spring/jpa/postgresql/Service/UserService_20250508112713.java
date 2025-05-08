package com.bezkoder.spring.jpa.postgresql.Service;


import java.util.Optional;

import com.bezkoder.spring.jpa.postgresql.Request.LoginRequest;
import com.bezkoder.spring.jpa.postgresql.model.User;
import com.bezkoder.spring.jpa.postgresql.repository.UsersRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;



@Service
public class UserService {

    @Autowired
    UsersRepo usersRepo;

    public User addUser(User user) {

        return usersRepo.save(user);

    }

    public Boolean loginUser(LoginRequest loginRequest) {

        Optional<User> user = usersRepo.findById(loginRequest.getUserId());
        User user1 = user.get();

        if(user1 == null) {
            return false;
        }



        if(!user1.getPassword().equals(loginRequest.getPassword())) {
            return false;
        }

        return true;



    }

}