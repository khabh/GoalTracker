package com.goaltracker.user.service;

import com.goaltracker.auth.domain.UserCredential;
import com.goaltracker.goal.dto.ActiveGoalDTO;
import com.goaltracker.goal.service.GoalService;
import com.goaltracker.user.constant.RelationType;
import com.goaltracker.user.domain.User;
import com.goaltracker.auth.dto.UserSignUpDTO;
import com.goaltracker.user.dto.*;
import com.goaltracker.user.dto.vo.FollowStatsVO;
import com.goaltracker.user.exception.ConnectionTargetNotFoundException;
import com.goaltracker.user.exception.follow.FollowActionTargetNotFound;
import com.goaltracker.user.exception.LoggedInUserNotFound;
import com.goaltracker.user.exception.UserNotFoundException;
import com.goaltracker.user.repository.UserRepository;
import com.goaltracker.user.util.UserConverter;
import com.goaltracker.user.util.UserProfileConverter;
import com.goaltracker.user.util.UsernameValidator;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Set;
import java.util.function.Function;

@Service
public class UserServiceImpl implements UserService {
    private final UserRepository userRepository;
    private final GoalService goalService;
    private final FollowRelationService followRelationService;

    @Autowired
    public UserServiceImpl(UserRepository userRepository, GoalService goalService, FollowRelationService followRelationService) {
        this.userRepository = userRepository;
        this.goalService = goalService;
        this.followRelationService = followRelationService;
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
    public UsernameValidationResponseDTO checkUsernameDuplication(String username) {
        if (!UsernameValidator.isValid(username)) {
            return UsernameValidationResponseDTO.invalidFormat();
        }
        if (userRepository.existsByUsername(username)) {
            return UsernameValidationResponseDTO.duplicated();
        }
        return UsernameValidationResponseDTO.valid();
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
    public UserProfileWithFollowStatsDTO getUserProfileWithFollowStats(Long targetUserId, Authentication userAuthentication) {
        User targetUser = userRepository.findById(targetUserId).orElseThrow(UserNotFoundException::new);
        Long currentUserId = getUserIdByUsername(userAuthentication.getName());
        RelationType userRelationType = getUserRelationShip(targetUser.getId(), currentUserId);
        FollowStatsVO followStats = followRelationService.getUserFollowStats(targetUser);

        return UserProfileConverter.toUserProfileWithFollowStats(targetUser, userRelationType, followStats);
    }

    @Override
    public List<ActiveGoalDTO> getUserActiveGoals(Long userId) {
        // getUserProfileWithFollowStats와 함께 실행되므로 user 검증 생략
        return goalService.getUserActiveGoals(userId);
    }

    @Override
    public void followNewUser(CreateFollowRelationDTO createFollowRelationDTO, String followerName) {
        User followee = userRepository.findById(createFollowRelationDTO.getFolloweeId())
                .orElseThrow(FollowActionTargetNotFound::new);
        User follower = getCurrentLoggedInUser(followerName);
        followRelationService.createFollowRelation(followee, follower);
    }

    @Override
    public void unfollowUser(Long followeeId, String followerName) {
        Long followerId = userRepository.findUserIdByUsername(followerName).orElseThrow(LoggedInUserNotFound::new);
        if (!userRepository.existsById(followeeId)) {
            throw new FollowActionTargetNotFound();
        }

        followRelationService.deleteFollowRelation(followeeId, followerId);
    }

    @Override
    public List<UserWithRelationDTO> getFollowersOfUser(Long targetUserId, String currentUsername) {
        return getConnectedUsersWithRelation(targetUserId, currentUsername, followRelationService::getFollowersOfUser);
    }

    @Override
    public List<UserWithRelationDTO> getUserFollowings(Long targetUserId, String currentUsername) {
        return getConnectedUsersWithRelation(targetUserId, currentUsername, followRelationService::getUserFollowings);
    }

    private User getCurrentLoggedInUser(String currentUsername) {
        return userRepository.findByUsername(currentUsername).orElseThrow(LoggedInUserNotFound::new);
    }

    private List<UserWithRelationDTO> getConnectedUsersWithRelation(Long targetUserId, String currentUsername, Function<User, List<User>> getConnectedUsers) {
        User currentUser = getCurrentLoggedInUser(currentUsername);
        User targetUser = userRepository.findById(targetUserId).orElseThrow(ConnectionTargetNotFoundException::new);
        Set<Long> currentUserFollowings = followRelationService.getUserFollowingIds(currentUser);
        List<User> usersConnectedWithTargetUser = getConnectedUsers.apply(targetUser);

        return UserConverter.toUserProfilesWithRelation(usersConnectedWithTargetUser, currentUser, currentUserFollowings);
    }

    private RelationType getUserRelationShip(Long targetUserId, Long currentUserId) {
        if (currentUserId.equals(targetUserId))
            return RelationType.SELF;

        boolean isFollowingTargetUser = followRelationService.isFollowRelationExists(targetUserId, currentUserId);
        if (isFollowingTargetUser)
            return RelationType.FOLLOWING;
        return RelationType.NOT_FOLLOWING;
    }
}
