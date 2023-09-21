package com.goaltracker.user.service;

import com.goaltracker.auth.domain.UserCredential;
import com.goaltracker.auth.dto.UserSignUpDTO;
import com.goaltracker.goal.dto.ActiveGoalDTO;
import com.goaltracker.user.domain.User;
import com.goaltracker.user.dto.*;
import org.springframework.security.core.Authentication;

import java.util.List;

public interface UserService {
    void signUpUserWithCredential(UserSignUpDTO userSignUpDTO, UserCredential userCredential);
    UserProfileEditViewDTO getEditViewWithoutProfileByUsername(String username);
    User getUserByUsername(String username);
    UsernameDuplicationCheckDTO checkUsernameDuplication(String username);
    EmailDuplicationCheckDTO checkEmailDuplication(String email);
    Long getUserIdByUsername(String username);
    UserProfileWithFollowStatsDTO getUserProfileWithFollowStats(Long targetUserId, Authentication loggedInAuthentication);
    List<ActiveGoalDTO> getUserActiveGoals(Long userId);
    void followNewUser(CreateFollowRelationDTO createFollowRelationDTO, String followerName);
    void unfollowUser(Long followeeId, String followerName);
}
