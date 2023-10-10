package com.goaltracker.user.service;

import com.goaltracker.user.domain.Interest;
import com.goaltracker.user.domain.User;
import com.goaltracker.user.domain.UserProfile;
import com.goaltracker.user.dto.UserProfileChangeDTO;
import com.goaltracker.user.repository.UserProfileRepository;
import com.goaltracker.user.util.UserProfileConverter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.*;
import java.util.stream.Collectors;

@Service
public class UserProfileServiceImpl implements UserProfileService {

    private final UserProfileRepository userProfileRepository;
    private final InterestService interestService;
    private final UserProfileInterestService userProfileInterestService;

    @Autowired
    public UserProfileServiceImpl(UserProfileRepository userProfileRepository, InterestService interestService, UserProfileInterestService userProfileInterestService) {
        this.userProfileRepository = userProfileRepository;
        this.interestService = interestService;
        this.userProfileInterestService = userProfileInterestService;
    }

    @Override
    public void changeUserProfile(User user, UserProfileChangeDTO userProfileChangeDTO) {
        Optional.ofNullable(user.getUserProfile())
                .ifPresentOrElse(userProfile -> editUserProfile(userProfile, userProfileChangeDTO),
                        () -> initializeUserProfile(user, userProfileChangeDTO));
    }

    private void editUserProfile(UserProfile userProfile, UserProfileChangeDTO userProfileChangeDTO) {
        userProfile.setNickname(userProfileChangeDTO.getNickname());
        userProfile.setIntroduction(userProfileChangeDTO.getIntroduction());
        editUserProfileInterests(userProfile, userProfileChangeDTO.getInterests());
    }

    private void initializeUserProfile(User user, UserProfileChangeDTO userProfileChangeDTO) {
        UserProfile createdUserProfile = createUserProfile(userProfileChangeDTO);
        user.setUserProfile(createdUserProfile);
    }

    private UserProfile createUserProfile(UserProfileChangeDTO userProfileChangeDTO) {
        UserProfile userProfile = UserProfileConverter.toUserProfile(userProfileChangeDTO);
        userProfileRepository.save(userProfile);
        if (!hasEmptyTagNames(userProfileChangeDTO.getInterests())) {
            createUserProfileInterests(userProfile, userProfileChangeDTO.getInterests());
        }

        return userProfile;
    }


    private void editUserProfileInterests(UserProfile userProfile, List<String> tagNames) {
        Set<Interest> currentInterests = userProfileInterestService.getInterestsByUserProfile(userProfile);
        if (hasEmptyTagNames(tagNames)) {
            userProfileInterestService.removeInterestsFromUserProfile(userProfile);
            return;
        }

        List<Interest> updatedInterests = interestService.getOrCreateInterestsByTagName(tagNames);
        Set<Interest> unChangedInterests = updatedInterests.stream()
                .filter(currentInterests::contains)
                .collect(Collectors.toSet());
        removeExistingInterests(userProfile, currentInterests, unChangedInterests);
        addNewInterests(userProfile, updatedInterests, unChangedInterests);
    }

    private void createUserProfileInterests(UserProfile userProfile, List<String> tagNames) {
        List<Interest> interests = interestService.getOrCreateInterestsByTagName(tagNames);
        userProfileInterestService.addInterestsToUserProfile(userProfile, interests);
    }

    private void removeExistingInterests(UserProfile userProfile, Set<Interest> currentInterests, Set<Interest> unchangedInterests) {
        Set<Interest> interestsToRemove = new HashSet<>(currentInterests);
        interestsToRemove.removeAll(unchangedInterests);
        if (!interestsToRemove.isEmpty()) {
            userProfileInterestService.removeInterestsFromUserProfile(userProfile, new ArrayList<>(interestsToRemove));
        }
    }

    private void addNewInterests(UserProfile userProfile, List<Interest> updatedInterests, Set<Interest> currentInterests) {
        List<Interest> interestsToAdd = updatedInterests.stream()
                .filter(interest -> !currentInterests.contains(interest))
                .collect(Collectors.toList());
        userProfileInterestService.addInterestsToUserProfile(userProfile, interestsToAdd);
    }

    private boolean hasEmptyTagNames(List<String> tagNames) {
        return tagNames == null || tagNames.isEmpty();
    }
}
