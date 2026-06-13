package com.raven.birdmail.controller;

import com.raven.birdmail.DTO.UserRegisterDTO;
import com.raven.birdmail.models.User;
import com.raven.birdmail.service.UserService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@CrossOrigin(origins = "http://127.0.0.1:5500/")
public class UserController {

    @Autowired
    private UserService userService;

    @RequestMapping(value = "birdmail/users", method = RequestMethod.POST)
    public ResponseEntity<User> create(@Valid @RequestBody UserRegisterDTO userDTO) {
        User createdUser = userService.createUser(userDTO);
        return new ResponseEntity<>(createdUser, HttpStatus.CREATED);
    }
}
