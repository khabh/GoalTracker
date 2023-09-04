package com.goaltracker.user.service;

import com.goaltracker.auth.domain.UserCredential;
import com.goaltracker.user.domain.User;
import com.goaltracker.auth.dto.UserSignUpDTO;
import com.goaltracker.user.repository.UserRepository;
import com.goaltracker.user.util.UserConverter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class UserServiceImpl implements UserService {
    private final UserRepository userRepository;

    @Autowired
    public UserServiceImpl(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    @Override
    public User signUpUserWithCredential(UserSignUpDTO userSignUpDTO, UserCredential userCredential) {
        User createdUser = UserConverter.toUser(userSignUpDTO, userCredential);
        userRepository.save(createdUser);

        return createdUser;
    }
}
