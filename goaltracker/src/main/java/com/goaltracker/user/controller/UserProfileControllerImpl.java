package com.goaltracker.user.controller;

import com.goaltracker.user.dto.UserProfileChangeDTO;
import com.goaltracker.user.service.UserAndUserProfileService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/goal-tracker/profiles")
public class UserProfileControllerImpl implements UserProfileController {

    private final UserAndUserProfileService userAndUserProfileService;

    @Autowired
    public UserProfileControllerImpl(UserAndUserProfileService userAndUserProfileService) {
        this.userAndUserProfileService = userAndUserProfileService;
    }

    @Override
    @GetMapping("/edit")
    @PreAuthorize("hasAnyRole('USER')")
    public String showUserProfileEditForm(Model model) {
        model.addAttribute("userProfile", userAndUserProfileService.getUserProfileEditView());
        return "goalTracker/profileEditForm";
    }

    @Override
    @PutMapping("/me")
    public ResponseEntity<String> createOrEditUserProfile(@Valid UserProfileChangeDTO userProfileChangeDTO) {
        userAndUserProfileService.editOrCreateUserProfile(userProfileChangeDTO);
        return ResponseEntity.ok("UserProfile updated successfully");
    }
}
