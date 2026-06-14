package com.raven.birdmail.controller;

import com.raven.birdmail.Repository.UserRepositoryImpl;
import com.raven.birdmail.Utils.JwtUtil;
import com.raven.birdmail.dto.LoginDTO;
import com.raven.birdmail.models.User;
import com.raven.birdmail.service.AuthService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class AuthController {

    @Autowired
    private AuthService authService;

    @PostMapping("birdmail/login")
    public ResponseEntity<?> login(@Valid @RequestBody LoginDTO loginDTO) {
        authService.login(loginDTO);

        return ResponseEntity.ok("Login successful");
    }
}
