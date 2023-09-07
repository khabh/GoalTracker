package com.goaltracker.user.util;

import com.goaltracker.user.domain.Interest;
import com.goaltracker.user.domain.UserProfile;
import com.goaltracker.user.domain.UserProfileInterest;

public class UserProfileInterestConverter {
    private UserProfileInterestConverter() {}

    public static UserProfileInterest toUserProfileInterest(UserProfile userProfile, Interest interest) {
        UserProfileInterest userProfileInterest = new UserProfileInterest();
        userProfileInterest.setUserProfile(userProfile);
        userProfileInterest.setInterest(interest);

        return userProfileInterest;
    }
}
