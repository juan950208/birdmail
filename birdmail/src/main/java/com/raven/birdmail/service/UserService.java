package com.raven.birdmail.service;

import com.raven.birdmail.DTO.UserRegisterDTO;
import com.raven.birdmail.Repository.UserRepository;
import com.raven.birdmail.models.User;
import de.mkammerer.argon2.Argon2;
import de.mkammerer.argon2.Argon2Factory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;

@Service
public class UserService {

    @Autowired
    private UserRepository userRepository;

    public User createUser(UserRegisterDTO userRegisterDTO) {

        if (userRepository.exists(userRegisterDTO.getUsername())) {
            throw new IllegalArgumentException("The username is already in use");
        }

        User newUser = new User();

        Argon2 argon2 = Argon2Factory.create(Argon2Factory.Argon2Types.ARGON2d);
        String hashedPassword = argon2.hash(1, 1024, 1, userRegisterDTO.getPassword());

        newUser.setUsername(userRegisterDTO.getUsername());
        newUser.setEmail(userRegisterDTO.getEmail());
        newUser.setFirstName(userRegisterDTO.getFirstName());
        newUser.setLastName(userRegisterDTO.getLastName());
        newUser.setPassword(hashedPassword);
        newUser.setCreatedAt(LocalDateTime.now());
        newUser.setActive(true);

        return userRepository.create(newUser);
    }
}
