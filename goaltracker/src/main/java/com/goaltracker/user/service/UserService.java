package com.goaltracker.user.service;

import com.goaltracker.auth.domain.UserCredential;
import com.goaltracker.auth.dto.UserSignUpDTO;
import com.goaltracker.user.domain.User;
import com.goaltracker.user.dto.EmailDuplicationCheckDTO;
import com.goaltracker.user.dto.UserProfileEditViewDTO;
import com.goaltracker.user.dto.UsernameDuplicationCheckDTO;

public interface UserService {
    User signUpUserWithCredential(UserSignUpDTO userSignUpDTO, UserCredential userCredential);
    UserProfileEditViewDTO getEditViewWithoutProfileByUsername(String username);
    User getUserByUsername(String username);
    UsernameDuplicationCheckDTO checkUsernameDuplication(String username);
    EmailDuplicationCheckDTO checkEmailDuplication(String email);
}
