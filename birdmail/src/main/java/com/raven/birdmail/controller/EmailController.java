package com.raven.birdmail.controller;

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
    EmailService emailService;

    @RequestMapping(value = "birdmail/send_email", method = RequestMethod.POST)
    public ResponseEntity<EmailResponseDTO> sendEmail(@RequestBody EmailDTO emailDTO) {

//        System.out.printf("***********\nReceived email\nsender: %s\nreceiver: %s\n" +
//                "subject: %s\nbody: %s\n***********\n", emailDTO.getSenderId(),
//                emailDTO.getRecipientId(), emailDTO.getSubject(),
//                emailDTO.getBody());

        Email createdEmail = emailService.sendEmail(emailDTO);
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

    @RequestMapping(value = "birdmail/emails/{email}")
    public ResponseEntity<List<EmailResponseDTO>> getReceivedEmails(
            @RequestHeader(value = "Authorization") String token,
            @PathVariable String email) {

        List<Email> result = emailService.getAllReceivedEmails(email);
        List<EmailResponseDTO> response = new ArrayList<>();

        for (Email e : result) {
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

    public ResponseEntity<List<EmailResponseDTO>> getSentEmails(String email) {
        return null;
    }

}
