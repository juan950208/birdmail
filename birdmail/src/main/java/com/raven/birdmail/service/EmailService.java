package com.raven.birdmail.service;

import com.raven.birdmail.models.User;
import com.raven.birdmail.repository.EmailRepository;
import com.raven.birdmail.repository.UserRepository;
import com.raven.birdmail.dto.EmailDTO;
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

    public Email sendEmail(EmailDTO emailDTO, String senderEmail) {

        if (userRepository.byId(emailDTO.getRecipientId()) == null ||
        userRepository.findByEmail(senderEmail) == null) {
            throw new UserNotFoundException("ERROR the user does not exist");
        }

        Email email = new Email();
        email.setSender(userRepository.findByEmail(senderEmail));
        email.setRecipient(userRepository.byId(emailDTO.getRecipientId()));
        email.setSubject(emailDTO.getSubject());
        email.setBody(emailDTO.getBody());
        email.setSentAt(LocalDateTime.now());

        return emailRepository.sendEmail(email);
    }

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
