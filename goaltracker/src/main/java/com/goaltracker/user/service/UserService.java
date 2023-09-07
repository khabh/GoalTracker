package com.goaltracker.user.service;

import com.goaltracker.auth.domain.UserCredential;
import com.goaltracker.auth.dto.UserSignUpDTO;
import com.goaltracker.user.domain.User;
import com.goaltracker.user.dto.UserProfileEditViewDTO;

public interface UserService {
    User signUpUserWithCredential(UserSignUpDTO userSignUpDTO, UserCredential userCredential);
    UserProfileEditViewDTO getEditViewWithoutProfileByUsername(String username);
    User getUserByUsername(String username);
}
