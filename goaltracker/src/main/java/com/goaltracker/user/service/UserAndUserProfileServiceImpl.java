package com.goaltracker.user.service;

import com.goaltracker.user.domain.User;
import com.goaltracker.user.dto.UserProfileChangeDTO;
import com.goaltracker.user.dto.UserProfileEditViewDTO;
import com.goaltracker.user.util.UserProfileConverter;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
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
    public UserProfileEditViewDTO getUserProfileEditView(String username) {
        User user = userService.getUserByUsername(username);
        return UserProfileConverter.toUserProfileEditView(user);
    }

    @Override
    @Transactional
    public void editOrCreateUserProfile(String username, UserProfileChangeDTO userProfileChangeDTO) {
        User user = userService.getUserByUsername(username);
        userProfileService.changeUserProfile(user, userProfileChangeDTO);
    }
}
