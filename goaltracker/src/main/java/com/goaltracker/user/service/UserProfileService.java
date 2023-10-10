package com.goaltracker.user.service;

import com.goaltracker.user.domain.User;
import com.goaltracker.user.dto.UserProfileChangeDTO;

public interface UserProfileService {
    void changeUserProfile(User user, UserProfileChangeDTO userProfileChangeDTO);
}
