package com.raven.birdmail.dto;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;
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
    @Pattern(regexp = "^[a-zA-Z0-9._]+$",
            message = "Only letters, numbers, dots and underscores allowed. No spaces.")
    private String email;

    @NotBlank(message = EMPTY_MSG)
    @Pattern(regexp = "^(?=.*[A-Z])(?=.*\\d).{8,}$",
            message =  """
        Password must contain:
        - at least 8 characters
        - one uppercase letter
        - one lowercase letter
        - one number
        """)
    private String password;

    @NotBlank(message = EMPTY_MSG)
    private String firstName;

    @NotBlank(message = EMPTY_MSG)
    private String lastName;
}
