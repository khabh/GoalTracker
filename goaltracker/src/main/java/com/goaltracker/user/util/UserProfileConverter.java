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
import java.util.Optional;
import java.util.stream.Collectors;

public class UserProfileConverter {

    private UserProfileConverter() {}

    private static String flattenInterests(List<UserProfileInterest> userProfileInterests) {
        return userProfileInterests.stream()
                .map(userProfileInterest -> userProfileInterest.getInterest().getTagName())
                .collect(Collectors.joining(", "));
    }


    public static UserProfileEditViewDTO toUserProfileEditView(User user) {
        return Optional.ofNullable(user.getUserProfile())
                .map(userProfile -> buildEditViewWithProfile(user, userProfile))
                .orElseGet(() -> buildEditViewWithoutProfile(user));
    }

    private static UserProfileEditViewDTO buildEditViewWithProfile(User user, UserProfile userProfile) {
        return UserProfileEditViewDTO.builder()
                .nickname(userProfile.getNickname())
                .email(user.getEmail())
                .introduction(userProfile.getIntroduction())
                .interests(flattenInterests(userProfile.getUserProfileInterests()))
                .build();
    }

    private static UserProfileEditViewDTO buildEditViewWithoutProfile(User user) {
        return UserProfileEditViewDTO.builder()
                .email(user.getEmail())
                .build();
    }

    public static UserProfile toUserProfile(UserProfileChangeDTO userProfileChangeDTO) {
        UserProfile userProfile = new UserProfile();
        userProfile.setIntroduction(userProfileChangeDTO.getIntroduction());
        userProfile.setNickname(userProfileChangeDTO.getNickname());

        return userProfile;
    }

    public static UserProfileWithFollowStatsDTO toUserProfileWithFollowStats(User user, RelationType relationType, FollowStatsVO followStats) {
        UserProfileWithFollowStatsDTO userProfileWithFollowStatsDTO = UserProfileWithFollowStatsDTO.builder()
                .userId(user.getId())
                .username(user.getUsername())
                .followerCount(followStats.getFollowerCount())
                .followingCount(followStats.getFollowingCount())
                .relationType(relationType)
                .build();

        return Optional.ofNullable(user.getUserProfile())
                .map(userProfile -> addProfileToFollowStatDTO(userProfile, userProfileWithFollowStatsDTO))
                .orElseGet(() -> userProfileWithFollowStatsDTO);
    }

    private static UserProfileWithFollowStatsDTO addProfileToFollowStatDTO(UserProfile userProfile, UserProfileWithFollowStatsDTO userProfileWithFollowStats) {
        List<String> interests = userProfile.getUserProfileInterests()
                .stream()
                .map(userProfileInterest -> userProfileInterest.getInterest().getTagName())
                .collect(Collectors.toList());
        userProfileWithFollowStats.setNickname(userProfile.getNickname());
        userProfileWithFollowStats.setInterests(interests);
        userProfileWithFollowStats.setIntroduction(userProfile.getIntroduction());

        return userProfileWithFollowStats;
    }
}
