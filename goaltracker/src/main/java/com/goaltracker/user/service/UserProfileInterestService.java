package com.goaltracker.user.service;

import com.goaltracker.user.domain.Interest;
import com.goaltracker.user.domain.UserProfile;

import java.util.List;
import java.util.Set;

public interface UserProfileInterestService {
    void addInterestsToUserProfile(UserProfile userProfile, List<Interest> interests);
    Set<Interest> getInterestsByUserProfile(UserProfile userProfile);
    void removeInterestsFromUserProfile(UserProfile userProfile, List<Interest> interests);
    void removeInterestsFromUserProfile(UserProfile userProfile);
}
