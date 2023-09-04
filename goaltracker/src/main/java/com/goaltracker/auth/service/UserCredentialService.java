package com.goaltracker.auth.service;

import com.goaltracker.auth.dto.SignInDTO;
import com.goaltracker.auth.dto.UserSignUpDTO;

public interface UserCredentialService {
    String authenticateUserWithJwtToken(SignInDTO signInDTO);
    String signUpAndAuthenticateUser(UserSignUpDTO userSignUpDTO);
}
