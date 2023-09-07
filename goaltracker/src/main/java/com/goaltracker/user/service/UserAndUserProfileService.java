package com.goaltracker.user.service;

import com.goaltracker.user.dto.UserProfileChangeDTO;
import com.goaltracker.user.dto.UserProfileEditViewDTO;

public interface UserAndUserProfileService {
    UserProfileEditViewDTO getUserProfileEditView();
    void editOrCreateUserProfile(UserProfileChangeDTO userProfileChangeDTO);
}
