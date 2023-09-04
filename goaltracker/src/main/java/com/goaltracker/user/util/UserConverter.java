package com.goaltracker.user.util;

import com.goaltracker.auth.domain.UserCredential;
import com.goaltracker.user.domain.User;
import com.goaltracker.auth.dto.UserSignUpDTO;

public class UserConverter {
    private UserConverter() {}

    public static User toUser(UserSignUpDTO userSignUpDTO, UserCredential userCredential) {
        User user = new User();
        user.setUsername(userSignUpDTO.getUsername());
        user.setEmail(userSignUpDTO.getEmail());
        user.setUserCredential(userCredential);

        return user;
    }
}
