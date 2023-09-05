package com.goaltracker.user.service;

import com.goaltracker.user.dto.UserProfileEditViewDTO;

public interface UserProfileService {
    UserProfileEditViewDTO getProfileEditView(String username);
}
