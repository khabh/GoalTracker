package com.goaltracker.auth.service;

import com.goaltracker.auth.domain.Authority;
import com.goaltracker.auth.domain.UserCredential;
import com.goaltracker.auth.dto.InitialPasswordDTO;
import com.goaltracker.auth.repository.UserCredentialRepository;
import com.goaltracker.auth.util.JwtProvider;
import com.goaltracker.auth.util.UserCredentialConverter;
import com.goaltracker.user.domain.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Set;

@Service
public class UserCredentialServiceImpl implements UserCredentialService {

    private static final String USER = "ROLE_USER";

    private final UserCredentialRepository userCredentialRepository;
    private final AuthorityService authorityService;
    private final JwtProvider tokenProvider;
    private final PasswordEncoder passwordEncoder;

    @Autowired
    public UserCredentialServiceImpl(UserCredentialRepository userCredentialRepository, AuthorityService authorityService, JwtProvider tokenProvider, PasswordEncoder passwordEncoder) {
        this.userCredentialRepository = userCredentialRepository;
        this.authorityService = authorityService;
        this.tokenProvider = tokenProvider;
        this.passwordEncoder = passwordEncoder;
    }


    @Override
    public UserCredential generateSaltedPassword(InitialPasswordDTO passwordDTO) {
        String hashedPassword = passwordEncoder.encode(passwordDTO.getPassword());
        Set<Authority> authorities = authorityService.getOrCreateAuthorities(List.of(USER));
        UserCredential userCredential = UserCredentialConverter.toUserCredential(hashedPassword, authorities);

        return userCredentialRepository.save(userCredential);
    }

    @Override
    public String generateToken(User user) {
        return tokenProvider.generateToken(user);
    }
}
