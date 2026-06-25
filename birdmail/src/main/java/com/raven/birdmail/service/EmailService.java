package com.raven.birdmail.service;

import com.raven.birdmail.dto.EmailRecipientDTO;
import com.raven.birdmail.dto.EmailListResponseDTO;
import com.raven.birdmail.dto.EmailResponseDTO;
import com.raven.birdmail.exception.EmailNotFoundException;
import com.raven.birdmail.exception.NotAuthorizedException;
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

    public EmailListResponseDTO sendEmail(SendEmailDTO sendEmailDTO, String senderEmail) {

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

        EmailListResponseDTO responseDTO = new EmailListResponseDTO();
        responseDTO.setSenderEmail(sender.getEmail());
        responseDTO.setSubject(savedEmail.getSubject());
        responseDTO.setDate(savedEmail.getDate());

        return responseDTO;
    }

    public List<EmailListResponseDTO> getAllReceivedEmails(String email) {

        User u = userRepository.findByEmail(email);
        List<EmailRecipient> emailRecipientList = emailRepository.getEmailRecipientByUser(u);
        List<EmailListResponseDTO> emailListResponseDTOList = new ArrayList<>();

        for (EmailRecipient emailRecipient : emailRecipientList) {
            EmailListResponseDTO emailListResponseDTO = new EmailListResponseDTO();
            Email receivedEmail = emailRecipient.getEmail();
            emailListResponseDTO.setSenderEmail(receivedEmail.getSender().getEmail());
            emailListResponseDTO.setSubject(receivedEmail.getSubject());
            emailListResponseDTO.setDate(receivedEmail.getDate());

            emailListResponseDTOList.add(emailListResponseDTO);
        }

        return emailListResponseDTOList;
    }

    public EmailResponseDTO getEmail(Long emailId, String loggedUserEmail) {

        Email email = emailRepository.findById(emailId);
        User user = userRepository.findByEmail(loggedUserEmail);

        if (email == null) {
            throw new EmailNotFoundException("Email not found");
        }

        if (!emailRepository.userHasAccessToEmail(user, email)) {
            throw new EmailNotFoundException("Not found");
        }

        EmailResponseDTO emailResponse = new EmailResponseDTO();
        emailResponse.setSenderEmail(email.getSender().getEmail());
        emailResponse.setSubject(email.getSubject());
        emailResponse.setBody(email.getBody());
        emailResponse.setDate(email.getDate());

        return emailResponse;
    }

    public List<Email> getAllSentEmails(String email) {
        User u = userRepository.findByEmail(email);

        if (u == null) {
            throw new UserNotFoundException("ERROR user not found");
        }

        return emailRepository.getAllSentEmails(u);
    }
}
