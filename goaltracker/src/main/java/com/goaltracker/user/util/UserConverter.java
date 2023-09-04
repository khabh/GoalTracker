package com.goaltracker.user.util;

import com.goaltracker.auth.domain.UserCredential;
import com.goaltracker.user.domain.User;
import com.goaltracker.user.dto.CreateUserDTO;

public class UserConverter {
    private UserConverter() {}

    public static User toUser(CreateUserDTO createUserDTO, UserCredential userCredential) {
        User user = new User();
        user.setUsername(createUserDTO.getUsername());
        user.setEmail(createUserDTO.getEmail());
        user.setUserCredential(userCredential);

        return user;
    }
}
