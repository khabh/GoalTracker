package com.goaltracker.auth.service;

import com.goaltracker.auth.domain.Authority;
import com.goaltracker.auth.domain.UserCredential;
import com.goaltracker.auth.dto.SignInDTO;
import com.goaltracker.auth.dto.UserSignUpDTO;
import com.goaltracker.auth.repository.UserCredentialRepository;
import com.goaltracker.auth.token.EmailPasswordAuthenticationToken;
import com.goaltracker.auth.util.TokenManager;
import com.goaltracker.auth.util.UserCredentialConverter;
import com.goaltracker.user.dto.EmailDuplicationCheckDTO;
import com.goaltracker.user.dto.UsernameValidationResponseDTO;
import com.goaltracker.user.exception.EmailDuplicatedException;
import com.goaltracker.user.exception.UsernameDuplicatedException;
import com.goaltracker.user.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.core.Authentication;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Set;

@Service
public class UserCredentialServiceImpl implements UserCredentialService {

    private static final String USER = "ROLE_USER";

    private final UserCredentialRepository userCredentialRepository;
    private final AuthorityService authorityService;
    private final UserService userService;
    private final AuthenticationManagerBuilder authenticationManagerBuilder;
    private final TokenManager tokenManager;
    private final PasswordEncoder passwordEncoder;

    @Autowired
    public UserCredentialServiceImpl(UserCredentialRepository userCredentialRepository, AuthorityService authorityService,
                                     UserService userService, AuthenticationManagerBuilder authenticationManagerBuilder,
                                     TokenManager tokenManager, PasswordEncoder passwordEncoder) {
        this.userCredentialRepository = userCredentialRepository;
        this.authorityService = authorityService;
        this.userService = userService;
        this.authenticationManagerBuilder = authenticationManagerBuilder;
        this.tokenManager = tokenManager;
        this.passwordEncoder = passwordEncoder;
    }

    @Override
    public void signUpUser(UserSignUpDTO userSignUpDTO) {
        checkUsernameAndEmailDuplication(userSignUpDTO);
        UserCredential userCredential = saveCredentialWithHashedPassword(userSignUpDTO.getPassword());
        userService.signUpUserWithCredential(userSignUpDTO, userCredential);
    }

    private void checkUsernameAndEmailDuplication(UserSignUpDTO userSignUpDTO) {
        UsernameValidationResponseDTO usernameValidationResponseDTO = userService.checkUsernameDuplication(userSignUpDTO.getUsername());
        if (usernameValidationResponseDTO.isDuplicated()) {
            throw new UsernameDuplicatedException();
        }

        EmailDuplicationCheckDTO emailDuplicationCheckDTO = userService.checkEmailDuplication(userSignUpDTO.getEmail());
        if (emailDuplicationCheckDTO.isDuplicated()) {
            throw new EmailDuplicatedException();
        }
    }

    private UserCredential saveCredentialWithHashedPassword(String rawPassword) {
        String hashedPassword = passwordEncoder.encode(rawPassword);
        Set<Authority> authorities = authorityService.getOrCreateAuthorities(List.of(USER));
        UserCredential userCredential = UserCredentialConverter.toUserCredential(hashedPassword, authorities);

        return userCredentialRepository.save(userCredential);
    }

    @Override
    public String authenticateUserWithJwtToken(SignInDTO signInDTO) {
        EmailPasswordAuthenticationToken authenticationToken = new EmailPasswordAuthenticationToken(signInDTO.getEmail(), signInDTO.getPassword());
        Authentication authentication = authenticationManagerBuilder.getObject().authenticate(authenticationToken);

        return tokenManager.generateToken(authentication);
    }
}
