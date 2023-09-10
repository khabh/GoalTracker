package com.goaltracker.user.controller;

import com.goaltracker.user.dto.UserProfileChangeDTO;
import org.springframework.http.ResponseEntity;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;

public interface UserProfileController {
    String showUserProfileEditForm(Model model);
    ResponseEntity<Object> createOrEditUserProfile(UserProfileChangeDTO userProfileChangeDTO, BindingResult userProfileChangeResult);
}
