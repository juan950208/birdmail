package com.raven.birdmail.service;

import com.raven.birdmail.dto.EmailRecipientDTO;
import com.raven.birdmail.models.EmailRecipient;
import com.raven.birdmail.models.User;
import com.raven.birdmail.repository.EmailRepository;
import com.raven.birdmail.repository.UserRepository;
import com.raven.birdmail.dto.SendEmailDTO;
import com.raven.birdmail.exception.UserNotFoundException;
import com.raven.birdmail.models.Email;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;

@Service
public class EmailService {

    @Autowired
    UserRepository userRepository;

    @Autowired
    EmailRepository emailRepository;

    public Email sendEmail(SendEmailDTO sendEmailDTO, String senderEmail) {

        Email email = new Email();
        email.setSender(userRepository.findByEmail(senderEmail));
        email.setSubject(sendEmailDTO.getSubject());
        email.setBody(sendEmailDTO.getBody());
        email.setDate(LocalDateTime.now());

        Email savedEmail = emailRepository.saveEmail(email);

        for (EmailRecipientDTO emailRecipientDTO : sendEmailDTO.getEmailRecipientDTOList()) {
            EmailRecipient emailRecipient = new EmailRecipient();
            emailRecipient.setEmail(savedEmail);
            emailRecipient.setRecipient(userRepository.byId(emailRecipientDTO.getRecipientId()));
            emailRecipient.setRecipientType(emailRecipientDTO.getRecipientType());

            emailRepository.saveEmailRecipientRelation(emailRecipient);
        }


        return null;
    }

//    public Email sendEmail(SendEmailDTO sendEmailDTO, String senderEmail) {
//
//        if (userRepository.byId(sendEmailDTO.getRecipientId()) == null ||
//        userRepository.findByEmail(senderEmail) == null) {
//            throw new UserNotFoundException("ERROR the user does not exist");
//        }
//
//        Email email = new Email();
//        email.setSender(userRepository.findByEmail(senderEmail));
//        email.setRecipient(userRepository.byId(sendEmailDTO.getRecipientId()));
//        email.setSubject(sendEmailDTO.getSubject());
//        email.setBody(sendEmailDTO.getBody());
//        email.setDate(LocalDateTime.now());
//
//        return emailRepository.sendEmail(email);
//    }

    public List<Email> getAllReceivedEmails(String email) {

        User u = userRepository.findByEmail(email);

        if (u == null) {
            throw new UserNotFoundException("ERROR user not found");
        }

        return emailRepository.getAllReceivedEmails(u);
    }

    public List<Email> getAllSentEmails(String email) {
        User u = userRepository.findByEmail(email);

        if (u == null) {
            throw new UserNotFoundException("ERROR user not found");
        }

        return emailRepository.getAllSentEmails(u);
    }
}
