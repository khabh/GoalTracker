package com.goaltracker.auth.util;

import com.goaltracker.auth.domain.Password;

public class PasswordConverter {

    private PasswordConverter() {}

    public static Password toPassword(String salt, String hashedPassword) {
        Password password = new Password();
        password.setHashedPassword(hashedPassword);
        password.setSalt(salt);

        return password;
    }
}
