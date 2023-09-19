package com.goaltracker.user.service;

import com.goaltracker.auth.domain.UserCredential;
import com.goaltracker.goal.dto.ActiveGoalDTO;
import com.goaltracker.goal.service.GoalService;
import com.goaltracker.user.constant.RelationType;
import com.goaltracker.user.domain.User;
import com.goaltracker.auth.dto.UserSignUpDTO;
import com.goaltracker.user.dto.EmailDuplicationCheckDTO;
import com.goaltracker.user.dto.UserProfileEditViewDTO;
import com.goaltracker.user.dto.UserProfileWithFollowStatsDTO;
import com.goaltracker.user.dto.UsernameDuplicationCheckDTO;
import com.goaltracker.user.dto.vo.FollowStatsVO;
import com.goaltracker.user.exception.UserNotFoundException;
import com.goaltracker.user.repository.FollowRelationRepository;
import com.goaltracker.user.repository.UserRepository;
import com.goaltracker.user.util.UserConverter;
import com.goaltracker.user.util.UserProfileConverter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class UserServiceImpl implements UserService {
    private final UserRepository userRepository;
    private final GoalService goalService;
    private final FollowRelationRepository followRelationRepository;

    @Autowired
    public UserServiceImpl(UserRepository userRepository, GoalService goalService, FollowRelationRepository followRelationRepository) {
        this.userRepository = userRepository;
        this.goalService = goalService;
        this.followRelationRepository = followRelationRepository;
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

    @Override
    public UserProfileWithFollowStatsDTO getUserProfileWithFollowStats(Long targetUserId, Authentication loggedInAuthentication) {
        User targetUser = userRepository.findById(targetUserId).orElseThrow(UserNotFoundException::new);
        Long loggedInUserId = getUserIdByUsername(loggedInAuthentication.getName());
        RelationType userRelationType = getUserRelationShip(targetUser.getId(), loggedInUserId);
        FollowStatsVO followStats = followRelationRepository.findFollowStatsByUser(targetUser);

        return UserProfileConverter.toUserProfileWithFollowStats(targetUser, userRelationType, followStats);
    }

    @Override
    public List<ActiveGoalDTO> getUserActiveGoals(Long userId) {
        // getUserProfileWithFollowStats와 함께 실행되므로 user 검증 생략
        return goalService.getUserActiveGoals(userId);
    }

    private RelationType getUserRelationShip(Long targetUserId, Long loggedInUserId) {
        if (loggedInUserId.equals(targetUserId))
            return RelationType.SELF;

        boolean isFollowingTargetUser = followRelationRepository.existsByFollowee_idAndFollower_id(targetUserId, loggedInUserId);
        if (isFollowingTargetUser)
            return RelationType.FOLLOWING;
        return RelationType.NOT_FOLLOWING;
    }
}
