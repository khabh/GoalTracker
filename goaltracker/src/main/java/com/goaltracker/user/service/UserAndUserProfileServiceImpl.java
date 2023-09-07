package com.goaltracker.user.service;

import com.goaltracker.user.domain.User;
import com.goaltracker.user.domain.UserProfile;
import com.goaltracker.user.dto.UserProfileChangeDTO;
import com.goaltracker.user.dto.UserProfileEditViewDTO;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

@Service
public class UserAndUserProfileServiceImpl implements UserAndUserProfileService {
    private final UserService userService;
    private final UserProfileService userProfileService;

    @Autowired
    public UserAndUserProfileServiceImpl(UserService userService, UserProfileService userProfileService) {
        this.userService = userService;
        this.userProfileService = userProfileService;
    }

    @Override
    public UserProfileEditViewDTO getUserProfileEditView() {
        String username = SecurityContextHolder.getContext().getAuthentication().getName();
        UserProfileEditViewDTO userProfileEditView = userProfileService.getProfileEditView(username);
        if (userProfileEditView != null)
            return userProfileEditView;
        return userService.getEditViewWithoutProfileByUsername(username);
    }

    @Override
    @Transactional
    public void editOrCreateUserProfile(UserProfileChangeDTO userProfileChangeDTO) {
        String username = SecurityContextHolder.getContext().getAuthentication().getName();
        User user = userService.getUserByUsername(username);
        if (user.getUserProfile() == null) {
            initializeUserProfileForUser(user, userProfileChangeDTO);
            return;
        }
        userProfileService.editUserProfile(user.getUserProfile(), userProfileChangeDTO);
    }

    private void initializeUserProfileForUser(User user, UserProfileChangeDTO userProfileChangeDTO) {
        UserProfile userProfile = userProfileService.createUserProfile(userProfileChangeDTO);
        user.setUserProfile(userProfile);
    }
}
