package com.goaltracker.user.controller;

import com.goaltracker.user.dto.UserProfileChangeDTO;
import com.goaltracker.user.service.UserAndUserProfileService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import java.util.HashMap;
import java.util.Map;

@Controller
@RequestMapping("/goal-tracker/profiles")
public class UserProfileControllerImpl implements UserProfileController {

    private final UserAndUserProfileService userAndUserProfileService;

    @Autowired
    public UserProfileControllerImpl(UserAndUserProfileService userAndUserProfileService) {
        this.userAndUserProfileService = userAndUserProfileService;
    }

    @Override
    @GetMapping("/me/edit")
    @PreAuthorize("hasAnyRole('USER')")
    public String showUserProfileEditForm(Model model) {
        model.addAttribute("userProfile", userAndUserProfileService.getUserProfileEditView());
        return "goalTracker/profileEditForm";
    }

    @Override
    @PutMapping("/me")
    public ResponseEntity<Object> createOrEditUserProfile(@Valid UserProfileChangeDTO userProfileChangeDTO, BindingResult userProfileChangeResult) {
        if (userProfileChangeResult.hasErrors()) {
            Map<String, String> errors = new HashMap<>();
            for (FieldError error : userProfileChangeResult.getFieldErrors()) {
                errors.put(error.getField(), error.getDefaultMessage());
            }
            return ResponseEntity.badRequest().body(errors);
        }
        userAndUserProfileService.editOrCreateUserProfile(userProfileChangeDTO);
        return ResponseEntity.ok()
                .header("Location", "/goal-tracker/profiles/me/edit")
                .build();
    }
}
