package com.raven.birdmail.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDateTime;
import java.util.List;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class EmailResponseDTO {
    private String senderEmail;
    private String subject;
    private String body;
    private LocalDateTime sentAt;
    private List<String> recipients;
}
