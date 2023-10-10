package com.goaltracker.user.util;

import java.util.regex.Pattern;

public class UsernameValidator {
    private final static Pattern usernamePattern = Pattern.compile("^[a-zA-Z0-9-_]+$");


    private UsernameValidator() {}

    public static boolean isValid(String username) {
        if (username.length() < 3 || username.length() > 15) {
            return false;
        }
        return usernamePattern.matcher(username).matches();
    }
}
