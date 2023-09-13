package com.goaltracker.user.service;

import com.goaltracker.auth.domain.UserCredential;
import com.goaltracker.user.domain.User;
import com.goaltracker.auth.dto.UserSignUpDTO;
import com.goaltracker.user.dto.EmailDuplicationCheckDTO;
import com.goaltracker.user.dto.UserProfileEditViewDTO;
import com.goaltracker.user.dto.UsernameDuplicationCheckDTO;
import com.goaltracker.user.repository.UserRepository;
import com.goaltracker.user.util.UserConverter;
import com.goaltracker.user.util.UserProfileConverter;
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
    public void signUpUserWithCredential(UserSignUpDTO userSignUpDTO, UserCredential userCredential) {
        User createdUser = UserConverter.toUser(userSignUpDTO, userCredential);
        userRepository.save(createdUser);
    }

    @Override
    public UserProfileEditViewDTO getEditViewWithoutProfileByUsername(String username){
        String email = userRepository.findEmailByUsername(username).orElseThrow();
        return UserProfileConverter.toUserProfileEditView(username, email);
    }

    @Override
    public User getUserByUsername(String username) {
        return userRepository.findByUsername(username).orElseThrow();
    }

    @Override
    public UsernameDuplicationCheckDTO checkUsernameDuplication(String username) {
        boolean isDuplicated = userRepository.existsByUsername(username);
        return new UsernameDuplicationCheckDTO(isDuplicated);
    }

    @Override
    public EmailDuplicationCheckDTO checkEmailDuplication(String email) {
        boolean isDuplicated = userRepository.existsByEmail(email);
        return new EmailDuplicationCheckDTO(isDuplicated);
    }

    @Override
    public Long getUserIdByUsername(String username) {
        return userRepository.findUserIdByUsername(username)
                .orElseThrow();
    }
}
