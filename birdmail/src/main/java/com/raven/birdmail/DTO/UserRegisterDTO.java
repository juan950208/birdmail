package com.raven.birdmail.DTO;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
public class UserRegisterDTO {

    private static final String EMPTY_MSG = "this field cannot be empty";

    @NotBlank(message = EMPTY_MSG)
    @Size(min = 5, max = 20, message = "The username must contain at least 5 characters")
    private String username;

    @NotBlank(message = EMPTY_MSG)
    @Email(message = "Email format not valid")
    private String email;

    @NotBlank(message = EMPTY_MSG)
    @Size(min = 8, message = "The password must contain at least 8 characters")
    private String password;

    @NotBlank(message = EMPTY_MSG)
    private String firstName;

    @NotBlank(message = EMPTY_MSG)
    private String lastName;
}
