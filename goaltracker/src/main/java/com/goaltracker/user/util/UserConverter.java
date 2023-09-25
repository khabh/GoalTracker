package com.goaltracker.user.util;

import com.goaltracker.auth.domain.UserCredential;
import com.goaltracker.user.constant.RelationType;
import com.goaltracker.user.domain.User;
import com.goaltracker.auth.dto.UserSignUpDTO;
import com.goaltracker.user.dto.UserWithRelationDTO;

import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;


public class UserConverter {
    private UserConverter() {}

    public static User toUser(UserSignUpDTO userSignUpDTO, UserCredential userCredential) {
        User user = new User();
        user.setUsername(userSignUpDTO.getUsername());
        user.setEmail(userSignUpDTO.getEmail());
        user.setUserCredential(userCredential);

        return user;
    }


    public static List<UserWithRelationDTO> toUserProfilesWithRelation(List<User> targetUsers, User currentUser, Set<Long> currentUserFollowings) {
        return targetUsers.stream()
                .map(targetUser -> UserWithRelationDTO.builder()
                        .userId(targetUser.getId())
                        .username(targetUser.getUsername())
                        .introduction(targetUser.getUserProfile().getIntroduction())
                        .relationType(getRelationType(currentUser, currentUserFollowings, targetUser))
                        .build())
                .collect(Collectors.toList());
    }

    private static RelationType getRelationType(User currentUser, Set<Long> currentUserFollowings, User targetUser) {
        if (currentUser.equals(targetUser)) {
            return RelationType.SELF;
        }
        if (currentUserFollowings.contains(targetUser.getId())) {
            return RelationType.FOLLOWING;
        }
        return RelationType.NOT_FOLLOWING;
    }
}
