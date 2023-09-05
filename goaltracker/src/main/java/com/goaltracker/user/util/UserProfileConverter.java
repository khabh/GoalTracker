package com.goaltracker.user.util;

import com.goaltracker.user.domain.Interest;
import com.goaltracker.user.domain.User;
import com.goaltracker.user.domain.UserProfile;
import com.goaltracker.user.dto.UserProfileEditViewDTO;

import java.util.Collection;
import java.util.stream.Collectors;

public class UserProfileConverter {

    private UserProfileConverter() {}

    private static String flattenInterests(Collection<Interest> interests) {
        return interests.stream()
                .map(Interest::getTagName).collect(Collectors.joining(", "));
    }

    public static UserProfileEditViewDTO toUserProfileEditView(UserProfile userProfile) {
        UserProfileEditViewDTO userProfileEditView = new UserProfileEditViewDTO();
        User user = userProfile.getUser();

        userProfileEditView.setIntroduction(userProfile.getIntroduction());
        userProfileEditView.setEmail(user.getEmail());
        userProfileEditView.setUsername(user.getUsername());
        userProfileEditView.setInterests(flattenInterests(userProfile.getInterests()));

        return userProfileEditView;
    }

    public static UserProfileEditViewDTO toUserProfileEditView(String username, String email) {
        UserProfileEditViewDTO userProfileEditView = new UserProfileEditViewDTO();
        userProfileEditView.setEmail(email);
        userProfileEditView.setUsername(username);

        return userProfileEditView;
    }
}
