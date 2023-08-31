package com.goaltracker.auth.util;

import java.security.SecureRandom;
import java.util.Base64;

public class SaltGenerator {
    private static final int EXTRA_SALT_LENGTH = 16;
    private static final int BOUND_OF_SALT_LENGTH = 17;

    private SaltGenerator() {}

    public static String generateSalt() {
        SecureRandom random = new SecureRandom();
        int saltLength = random.nextInt(BOUND_OF_SALT_LENGTH) + EXTRA_SALT_LENGTH;
        byte[] salt = new byte[saltLength];
        random.nextBytes(salt);

        return Base64.getEncoder().encodeToString(salt);
    }
}
