package com.goaltracker.user.service;

import com.goaltracker.auth.domain.UserCredential;
import com.goaltracker.auth.dto.InitialPasswordDTO;
import com.goaltracker.auth.service.UserCredentialService;
import com.goaltracker.user.domain.User;
import com.goaltracker.user.dto.CreateUserDTO;
import com.goaltracker.user.repository.UserRepository;
import com.goaltracker.user.util.UserConverter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class UserServiceImpl implements UserService {
    private final UserCredentialService userCredentialService;
    private final UserRepository userRepository;

    @Autowired
    public UserServiceImpl(UserCredentialService userCredentialService, UserRepository userRepository) {
        this.userCredentialService = userCredentialService;
        this.userRepository = userRepository;
    }

    @Override
    public String signUpNewUser(CreateUserDTO createUserDTO, InitialPasswordDTO initialPasswordDTO) {
        UserCredential userCredential = userCredentialService.generateSaltedPassword(initialPasswordDTO);
        User createdUser = UserConverter.toUser(createUserDTO, userCredential);
        userRepository.save(createdUser);

        return userCredentialService.generateToken(createdUser);
    }
}
