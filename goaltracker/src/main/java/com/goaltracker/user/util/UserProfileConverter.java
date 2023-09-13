package com.goaltracker.user.util;

import com.goaltracker.user.constant.RelationType;
import com.goaltracker.user.domain.User;
import com.goaltracker.user.domain.UserProfile;
import com.goaltracker.user.domain.UserProfileInterest;
import com.goaltracker.user.dto.UserProfileChangeDTO;
import com.goaltracker.user.dto.UserProfileEditViewDTO;
import com.goaltracker.user.dto.UserProfileWithFollowStatsDTO;
import com.goaltracker.user.dto.vo.FollowStatsVO;

import java.util.List;
import java.util.stream.Collectors;

public class UserProfileConverter {

    private UserProfileConverter() {}

    private static String flattenInterests(List<UserProfileInterest> userProfileInterests) {
        return userProfileInterests.stream()
                .map(userProfileInterest -> userProfileInterest.getInterest().getTagName())
                .collect(Collectors.joining(", "));
    }

    public static UserProfileEditViewDTO toUserProfileEditView(UserProfile userProfile) {
        UserProfileEditViewDTO userProfileEditView = new UserProfileEditViewDTO();
        List<UserProfileInterest> userProfileInterests = userProfile.getUserProfileInterests();
        User user = userProfile.getUser();

        userProfileEditView.setIntroduction(userProfile.getIntroduction());
        userProfileEditView.setEmail(user.getEmail());
        userProfileEditView.setUsername(user.getUsername());
        if (userProfileInterests != null) {
            userProfileEditView.setInterests(flattenInterests(userProfileInterests));
        }

        return userProfileEditView;
    }

    public static UserProfileEditViewDTO toUserProfileEditView(String username, String email) {
        UserProfileEditViewDTO userProfileEditView = new UserProfileEditViewDTO();
        userProfileEditView.setEmail(email);
        userProfileEditView.setUsername(username);

        return userProfileEditView;
    }

    public static UserProfile toUserProfile(UserProfileChangeDTO userProfileChangeDTO) {
        UserProfile userProfile = new UserProfile();
        userProfile.setIntroduction(userProfileChangeDTO.getIntroduction());

        return userProfile;
    }

    public static UserProfileWithFollowStatsDTO toUserProfileWithFollowStats(User user, RelationType relationType, FollowStatsVO followStats) {
        UserProfile userProfile = user.getUserProfile();
        List<String> interests = userProfile.getUserProfileInterests()
                .stream()
                .map(userProfileInterest -> userProfileInterest.getInterest().getTagName())
                .collect(Collectors.toList());

        return UserProfileWithFollowStatsDTO.builder()
                .username(user.getUsername())
                .followingCount(followStats.getFollowingCount())
                .followerCount(followStats.getFollowerCount())
                .description(userProfile.getIntroduction())
                .interests(interests)
                .relationType(relationType)
                .build();
    }
}
