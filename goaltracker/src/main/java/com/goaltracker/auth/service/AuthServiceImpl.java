package com.goaltracker.auth.service;

import com.goaltracker.auth.domain.Password;
import com.goaltracker.auth.dto.InitialPasswordDTO;
import com.goaltracker.auth.repository.PasswordRepository;
import com.goaltracker.auth.util.PasswordConverter;
import com.goaltracker.auth.util.SaltGenerator;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
public class AuthServiceImpl implements AuthService {
    private final PasswordEncoder passwordEncoder = new BCryptPasswordEncoder();
    private final PasswordRepository passwordRepository;

    @Autowired
    public AuthServiceImpl(PasswordRepository passwordRepository) {
        this.passwordRepository = passwordRepository;
    }

    @Override
    public Password generateSaltedPassword(InitialPasswordDTO passwordDTO) {
        String salt = SaltGenerator.generateSalt();
        String hashedPassword = getHashedPassword(salt, passwordDTO.getPassword());
        Password password = PasswordConverter.toPassword(salt, hashedPassword);

        return passwordRepository.save(password);
    }

    private String getHashedPassword(String salt, String rawPassword) {
        return passwordEncoder.encode(rawPassword + salt);
    }
}
