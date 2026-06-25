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
public class EmailListResponseDTO {
    private String senderEmail;
    private String subject;
    private LocalDateTime date;
}
