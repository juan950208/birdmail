package com.raven.birdmail.controller;

import com.raven.birdmail.Utils.JwtUtil;
import com.raven.birdmail.dto.EmailRecipientDTO;
import com.raven.birdmail.dto.SendEmailDTO;
import com.raven.birdmail.dto.EmailResponseDTO;
import com.raven.birdmail.models.Email;
import com.raven.birdmail.service.EmailService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@RestController
public class EmailController {

    @Autowired
    JwtUtil jwtUtil;

    @Autowired
    EmailService emailService;

    @RequestMapping(value = "birdmail/send_email2", method = RequestMethod.POST)
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

//    @RequestMapping(value = "birdmail/inbox")
//    public ResponseEntity<List<EmailResponseDTO>> getReceivedEmails(
//            @RequestHeader(value = "Authorization") String token) {
//
//        if (!jwtUtil.validateToken(token)) {
//            return ResponseEntity.status(401).build();
//        }
//
//        String email = jwtUtil.getEmailFromToken(token);
//
//        List<Email> emails = emailService.getAllReceivedEmails(email);
//        List<EmailResponseDTO> response = new ArrayList<>();
//
//        for (Email e : emails) {
//            EmailResponseDTO emailDTO = new EmailResponseDTO(
//                    e.getSender().getEmail(),
//                    e.getRecipient().getEmail(),
//                    e.getSubject(),
//                    e.getBody(),
//                    e.getDate()
//            );
//
//            response.add(emailDTO);
//        }
//
//        return ResponseEntity.status(HttpStatus.OK)
//                .body(response);
//    }
//
//    @RequestMapping(value = "birdmail/sent")
//    public ResponseEntity<List<EmailResponseDTO>> getSentEmails(
//            @RequestHeader(value = "Authorization") String token) {
//
//        if (!jwtUtil.validateToken(token)) {
//            return ResponseEntity.status(401).build();
//        }
//
//        String email = jwtUtil.getEmailFromToken(token);
//
//        List<Email> emails = emailService.getAllSentEmails(email);
//        List<EmailResponseDTO> response = new ArrayList<>();
//
//        for (Email e : emails) {
//            EmailResponseDTO emailDTO = new EmailResponseDTO(
//                    e.getSender().getEmail(),
//                    e.getRecipient().getEmail(),
//                    e.getSubject(),
//                    e.getBody(),
//                    e.getDate()
//            );
//
//            response.add(emailDTO);
//        }
//
//        return ResponseEntity.status(HttpStatus.OK)
//                .body(response);
//
//    }

}
