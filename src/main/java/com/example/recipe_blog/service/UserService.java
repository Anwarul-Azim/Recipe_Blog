package com.example.recipe_blog.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;
import com.example.recipe_blog.model.User;
import com.example.recipe_blog.repository.UserRepository;

@Service
public class UserService {

    private final UserRepository userRepository;
    private final PasswordEncoder encoder;

    @Autowired
    public UserService(UserRepository userRepository, PasswordEncoder encoder) {
        this.userRepository = userRepository;
        this.encoder = encoder;
    }


    public void saveUser(User user) {

        if (!userRepository.existsById(user.getEmail())) {
            user.setPassword(encoder.encode(user.getPassword()));
            user.setRole("User");
            userRepository.save(user);
        } else {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST);
        }

    }

    public User getUser(String email) {
        User user = userRepository.findById(email).get();
        return user;
    }

}

