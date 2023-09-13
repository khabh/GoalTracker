package com.goaltracker.user.service;

import com.goaltracker.auth.domain.UserCredential;
import com.goaltracker.auth.dto.UserSignUpDTO;
import com.goaltracker.user.domain.User;
import com.goaltracker.user.dto.EmailDuplicationCheckDTO;
import com.goaltracker.user.dto.UserProfileEditViewDTO;
import com.goaltracker.user.dto.UserProfileWithFollowStatsDTO;
import com.goaltracker.user.dto.UsernameDuplicationCheckDTO;
import org.springframework.security.core.Authentication;

public interface UserService {
    void signUpUserWithCredential(UserSignUpDTO userSignUpDTO, UserCredential userCredential);
    UserProfileEditViewDTO getEditViewWithoutProfileByUsername(String username);
    User getUserByUsername(String username);
    UsernameDuplicationCheckDTO checkUsernameDuplication(String username);
    EmailDuplicationCheckDTO checkEmailDuplication(String email);
    Long getUserIdByUsername(String username);
    UserProfileWithFollowStatsDTO getUserProfileForDashBoard(Long userId, Authentication userAuthentication);
}
