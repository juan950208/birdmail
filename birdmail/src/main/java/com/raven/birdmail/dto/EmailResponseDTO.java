package com.raven.birdmail.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDateTime;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class EmailResponseDTO {
    private String senderEmail;
    private String recipientEmail;
    private String subject;
    private String body;
    private LocalDateTime sentAt;
}
