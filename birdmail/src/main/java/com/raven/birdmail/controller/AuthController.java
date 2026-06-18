package com.raven.birdmail.controller;

import com.raven.birdmail.Utils.JwtUtil;
import com.raven.birdmail.dto.LoginDTO;
import com.raven.birdmail.dto.LoginResponseDTO;
import com.raven.birdmail.models.User;
import com.raven.birdmail.service.AuthService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
@CrossOrigin(origins = "http://127.0.0.1:5500/")
public class AuthController {

    @Autowired
    private AuthService authService;

    @Autowired
    JwtUtil jwtUtil;

    @PostMapping("birdmail/login")
    public ResponseEntity<LoginResponseDTO> login(@Valid @RequestBody LoginDTO loginDTO) {
        User loggedUser = authService.login(loginDTO);

        String token = jwtUtil.generateToken(loggedUser);
        String email = jwtUtil.getEmailFromToken(token);

        return ResponseEntity.ok(new LoginResponseDTO(token, email));
    }
}
