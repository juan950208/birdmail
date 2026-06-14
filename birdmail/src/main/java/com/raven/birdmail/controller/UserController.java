package com.raven.birdmail.controller;

import com.raven.birdmail.Utils.JwtUtil;
import com.raven.birdmail.dto.UserRegisterDTO;
import com.raven.birdmail.dto.UserResponseDTO;
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

    @Autowired
    JwtUtil jwtUtil;

    @RequestMapping(value = "birdmail/users", method = RequestMethod.POST)
    public ResponseEntity<UserResponseDTO> create(@Valid @RequestBody UserRegisterDTO userDTO) {
        User createdUser = userService.createUser(userDTO);

        UserResponseDTO response = new UserResponseDTO(
                createdUser.getId(),
                createdUser.getFirstName(),
                createdUser.getLastName(),
                createdUser.getEmail()
        );

        return ResponseEntity.status(HttpStatus.CREATED)
                .body(response);
    }
}
