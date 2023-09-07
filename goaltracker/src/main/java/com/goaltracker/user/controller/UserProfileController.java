package com.goaltracker.user.controller;

import com.goaltracker.user.dto.UserProfileChangeDTO;
import org.springframework.http.ResponseEntity;
import org.springframework.ui.Model;

public interface UserProfileController {
    String showUserProfileEditForm(Model model);
    ResponseEntity<String> createOrEditUserProfile(UserProfileChangeDTO userProfileChangeDTO);
}
