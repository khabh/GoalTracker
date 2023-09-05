package com.goaltracker.user.service;

import com.goaltracker.user.domain.UserProfile;
import com.goaltracker.user.dto.UserProfileEditViewDTO;
import com.goaltracker.user.repository.UserProfileRepository;
import com.goaltracker.user.util.UserProfileConverter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class UserProfileServiceImpl implements UserProfileService {

    private final UserProfileRepository userProfileRepository;

    @Autowired
    public UserProfileServiceImpl(UserProfileRepository userProfileRepository) {
        this.userProfileRepository = userProfileRepository;
    }

    @Override
    public UserProfileEditViewDTO getProfileEditView(String username) {
        UserProfile userProfile = userProfileRepository.getUserProfileByUser_Username(username);
        if (userProfile == null)
            return null;
        return UserProfileConverter.toUserProfileEditView(userProfile);
    }
}
