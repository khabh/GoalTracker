package com.goaltracker.user.service;

import com.goaltracker.user.domain.UserProfile;
import com.goaltracker.user.dto.UserProfileChangeDTO;
import com.goaltracker.user.dto.UserProfileEditViewDTO;

public interface UserProfileService {
    UserProfileEditViewDTO getProfileEditView(String username);
    UserProfile createUserProfile(UserProfileChangeDTO userProfileChangeDTO);
    void editUserProfile(UserProfile userProfile, UserProfileChangeDTO userProfileChangeDTO);
}
