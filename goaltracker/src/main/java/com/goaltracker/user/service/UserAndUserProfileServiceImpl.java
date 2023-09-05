package com.goaltracker.user.service;

import com.goaltracker.user.dto.UserProfileEditViewDTO;
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
}
