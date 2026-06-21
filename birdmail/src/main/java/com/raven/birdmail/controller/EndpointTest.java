package com.raven.birdmail.controller;

import com.raven.birdmail.repository.UserRepository;
import com.raven.birdmail.dto.UserResponseDTO;
import com.raven.birdmail.exception.UserNotFoundException;
import com.raven.birdmail.models.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class EndpointTest {

    @Autowired
    UserRepository repository;

    @RequestMapping(value = "birdmail/users/{id}")
    public ResponseEntity<UserResponseDTO> findUserById(@PathVariable Long id) {
        User user = repository.byId(id);
        UserResponseDTO result = new UserResponseDTO();

        if (user == null) {
            throw new UserNotFoundException("User not found");
        }

        result.setEmail(user.getEmail());
        result.setId(user.getId());
        result.setFirstName(user.getFirstName());
        result.setLastName(user.getLastName());

        return ResponseEntity.status(HttpStatus.OK)
                .body(result);
    }


}
