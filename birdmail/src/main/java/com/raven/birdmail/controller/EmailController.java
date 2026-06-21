package com.raven.birdmail.controller;

import com.raven.birdmail.Utils.JwtUtil;
import com.raven.birdmail.dto.EmailDTO;
import com.raven.birdmail.dto.EmailResponseDTO;
import com.raven.birdmail.models.Email;
import com.raven.birdmail.service.EmailService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
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
            @RequestBody EmailDTO emailDTO) {

        if (!jwtUtil.validateToken(token)) {
            return ResponseEntity.status(401).build();
        }

        String senderEmail = jwtUtil.getEmailFromToken(token);

        Email createdEmail = emailService.sendEmail(emailDTO, senderEmail);
        EmailResponseDTO response = new EmailResponseDTO(
                createdEmail.getSender().getEmail(),
                createdEmail.getRecipient().getEmail(),
                createdEmail.getSubject(),
                createdEmail.getBody(),
                createdEmail.getSentAt()
        );

        return ResponseEntity.status(HttpStatus.CREATED)
                .body(response);
    }

    @RequestMapping(value = "birdmail/inbox")
    public ResponseEntity<List<EmailResponseDTO>> getReceivedEmails(
            @RequestHeader(value = "Authorization") String token) {

        if (!jwtUtil.validateToken(token)) {
            return ResponseEntity.status(401).build();
        }

        String email = jwtUtil.getEmailFromToken(token);

        List<Email> emails = emailService.getAllReceivedEmails(email);
        List<EmailResponseDTO> response = new ArrayList<>();

        for (Email e : emails) {
            EmailResponseDTO emailDTO = new EmailResponseDTO(
                    e.getSender().getEmail(),
                    e.getRecipient().getEmail(),
                    e.getSubject(),
                    e.getBody(),
                    e.getSentAt()
            );

            response.add(emailDTO);
        }

        return ResponseEntity.status(HttpStatus.OK)
                .body(response);
    }

    @RequestMapping(value = "birdmail/sent")
    public ResponseEntity<List<EmailResponseDTO>> getSentEmails(
            @RequestHeader(value = "Authorization") String token) {

        if (!jwtUtil.validateToken(token)) {
            return ResponseEntity.status(401).build();
        }

        String email = jwtUtil.getEmailFromToken(token);

        List<Email> emails = emailService.getAllSentEmails(email);
        List<EmailResponseDTO> response = new ArrayList<>();

        for (Email e : emails) {
            EmailResponseDTO emailDTO = new EmailResponseDTO(
                    e.getSender().getEmail(),
                    e.getRecipient().getEmail(),
                    e.getSubject(),
                    e.getBody(),
                    e.getSentAt()
            );

            response.add(emailDTO);
        }

        return ResponseEntity.status(HttpStatus.OK)
                .body(response);

    }

}
