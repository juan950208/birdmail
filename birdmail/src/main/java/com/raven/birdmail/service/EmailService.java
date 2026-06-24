package com.raven.birdmail.service;

import com.raven.birdmail.dto.EmailRecipientDTO;
import com.raven.birdmail.dto.EmailResponseDTO;
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
import java.util.ArrayList;
import java.util.List;

@Service
public class EmailService {

    @Autowired
    UserRepository userRepository;

    @Autowired
    EmailRepository emailRepository;

    public EmailResponseDTO sendEmail(SendEmailDTO sendEmailDTO, String senderEmail) {

        List<String> recipientsEmail = new ArrayList<>();

        Email email = new Email();
        User sender = userRepository.findByEmail(senderEmail);
        email.setSender(sender);
        email.setSubject(sendEmailDTO.getSubject());
        email.setBody(sendEmailDTO.getBody());
        email.setDate(LocalDateTime.now());

        Email savedEmail = emailRepository.saveEmail(email);

        for (EmailRecipientDTO emailRecipientDTO : sendEmailDTO.getRecipients()) {
            EmailRecipient emailRecipient = new EmailRecipient();
            emailRecipient.setEmail(savedEmail);
            User recipient = userRepository.byId(emailRecipientDTO.getRecipientId());
            emailRecipient.setRecipient(recipient);
            emailRecipient.setRecipientType(emailRecipientDTO.getRecipientType());

            recipientsEmail.add(recipient.getEmail());
            emailRepository.saveEmailRecipientRelation(emailRecipient);
        }

        EmailResponseDTO responseDTO = new EmailResponseDTO();
        responseDTO.setSenderEmail(sender.getEmail());
        responseDTO.setSubject(savedEmail.getSubject());
        responseDTO.setBody(savedEmail.getBody());
        responseDTO.setRecipients(recipientsEmail);

        return responseDTO;
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

    public List<EmailResponseDTO> getAllReceivedEmails(String email) {

        User u = userRepository.findByEmail(email);
        List<EmailRecipient> emailRecipientList = emailRepository.getEmailRecipientByUser(u);
        List<EmailResponseDTO> emailResponseDTOList = new ArrayList<>();

        for (EmailRecipient emailRecipient : emailRecipientList) {
            EmailResponseDTO emailResponseDTO = new EmailResponseDTO();
            Email receivedEmail = emailRecipient.getEmail();
            emailResponseDTO.setSenderEmail(receivedEmail.getSender().getEmail());
            emailResponseDTO.setSubject(receivedEmail.getSubject());
            emailResponseDTO.setBody(receivedEmail.getBody());
            emailResponseDTO.setSentAt(receivedEmail.getDate());

            emailResponseDTOList.add(emailResponseDTO);
        }

        return emailResponseDTOList;
    }

    public List<Email> getAllSentEmails(String email) {
        User u = userRepository.findByEmail(email);

        if (u == null) {
            throw new UserNotFoundException("ERROR user not found");
        }

        return emailRepository.getAllSentEmails(u);
    }
}
