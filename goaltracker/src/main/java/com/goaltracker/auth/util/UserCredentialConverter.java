package com.goaltracker.auth.util;

import com.goaltracker.auth.domain.Authority;
import com.goaltracker.auth.domain.UserCredential;

import java.util.Set;

public class UserCredentialConverter {

    private UserCredentialConverter() {}

    public static UserCredential toUserCredential(String hashedPassword, Set<Authority> authorities) {
        UserCredential userCredential = new UserCredential();
        userCredential.setHashedPassword(hashedPassword);
        userCredential.setAuthorities(authorities);

        return userCredential;
    }
}
