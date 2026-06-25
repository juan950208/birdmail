package com.raven.birdmail.controller;

import com.raven.birdmail.Utils.JwtUtil;
import com.raven.birdmail.dto.EmailResponseDTO;
import com.raven.birdmail.dto.SendEmailDTO;
import com.raven.birdmail.dto.EmailListResponseDTO;
import com.raven.birdmail.exception.NotAuthorizedException;
import com.raven.birdmail.models.User;
import com.raven.birdmail.service.EmailService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
public class EmailController {

    @Autowired
    JwtUtil jwtUtil;

    @Autowired
    EmailService emailService;

    @RequestMapping(value = "birdmail/send_email", method = RequestMethod.POST)
    public ResponseEntity<EmailResponseDTO> sendEmail(
            @RequestHeader(value = "Authorization") String token,
            @RequestBody SendEmailDTO sendEmailDTO) {

        if (!jwtUtil.validateToken(token)) {
            return ResponseEntity.status(401).build();
        }

        String senderEmail = jwtUtil.getEmailFromToken(token);

        EmailResponseDTO createdEmail = emailService.sendEmail(sendEmailDTO, senderEmail);

        return ResponseEntity.status(HttpStatus.CREATED)
                .body(createdEmail);
    }

    @RequestMapping(value = "birdmail/inbox")
    public ResponseEntity<List<EmailListResponseDTO>> loadReceivedEmails(
            @RequestHeader(value = "Authorization") String token) {

        if (!jwtUtil.validateToken(token)) {
            throw new NotAuthorizedException("Not authorized");
        }

        String email = jwtUtil.getEmailFromToken(token);

        return ResponseEntity.status(HttpStatus.OK)
                .body(emailService.getAllReceivedEmails(email));
    }

    @RequestMapping(value = "birdmail/sent")
    public ResponseEntity<List<EmailListResponseDTO>> loadSentEmails(
            @RequestHeader(value = "Authorization") String token) {

        if (!jwtUtil.validateToken(token)) {
            throw new NotAuthorizedException("Not authorized");
        }

        String email = jwtUtil.getEmailFromToken(token);

        return ResponseEntity.status(HttpStatus.OK)
                .body(emailService.getAllSentEmails(email));
    }

    @RequestMapping(value = "birdmail/email/{id}")
    public ResponseEntity<EmailResponseDTO> getEmail(
            @RequestHeader(value = "Authorization", required = false) String token,
            @PathVariable Long id) {

        if (token == null || !jwtUtil.validateToken(token)) {
            throw new NotAuthorizedException("User has not been authenticated");
        }

        String loggedUserEmail = jwtUtil.getEmailFromToken(token);

        return ResponseEntity.status(HttpStatus.OK)
                .body(emailService.getEmail(id, loggedUserEmail));
    }
}
