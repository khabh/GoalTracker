package com.goaltracker.user.service;

import com.goaltracker.user.domain.Interest;
import com.goaltracker.user.domain.UserProfile;
import com.goaltracker.user.domain.UserProfileInterest;
import com.goaltracker.user.repository.UserProfileInterestRepository;
import com.goaltracker.user.util.UserProfileInterestConverter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

@Service
public class UserProfileInterestServiceImpl implements UserProfileInterestService {

    private final UserProfileInterestRepository userProfileInterestRepository;

    @Autowired
    public UserProfileInterestServiceImpl(UserProfileInterestRepository userProfileInterestRepository) {
        this.userProfileInterestRepository = userProfileInterestRepository;
    }

    @Override
    public void addInterestsToUserProfile(UserProfile userProfile, List<Interest> interests) {
        if (interests.isEmpty())
            return;
        List<UserProfileInterest> userProfileInterests = interests.stream()
                .map(interest -> UserProfileInterestConverter.toUserProfileInterest(userProfile, interest))
                .collect(Collectors.toList());
        userProfileInterestRepository.saveAll(userProfileInterests);
    }

    @Override
    public Set<Interest> getInterestsByUserProfile(UserProfile userProfile) {
        return userProfileInterestRepository.getInterestsByUserProfile(userProfile);
    }

    @Override
    public void removeInterestsFromUserProfile(UserProfile userProfile, List<Interest> interests) {
        userProfileInterestRepository.deleteByUserProfileAndInterestIn(userProfile, interests);
    }

    @Override
    public void removeInterestsFromUserProfile(UserProfile userProfile) {
        userProfileInterestRepository.deleteByUserProfile(userProfile);
    }
}
