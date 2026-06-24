package com.raven.birdmail.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class SendEmailDTO {
    private String subject;
    private String body;
    private List<EmailRecipientDTO> recipients;
}
