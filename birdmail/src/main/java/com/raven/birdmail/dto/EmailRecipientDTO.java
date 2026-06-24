package com.raven.birdmail.dto;

import com.raven.birdmail.models.RecipientType;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class EmailRecipientDTO {
    private Long recipientId;
    private RecipientType recipientType;
}
