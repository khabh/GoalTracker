package com.goaltracker.auth.dto;

import com.goaltracker.user.validator.annotation.NoWhitespace;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class SignInDTO {

    @Email
    @NotBlank
    private String email;

    @NoWhitespace
    private String password;
}
