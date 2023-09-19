package com.goaltracker.user.controller;

import com.goaltracker.user.dto.UsernameDuplicationCheckDTO;
import org.springframework.http.ResponseEntity;
import org.springframework.ui.Model;

public interface UserController {
    ResponseEntity<UsernameDuplicationCheckDTO> checkUsernameDuplication(String username);
    String redirectToUserActiveGoals();
    String showUserAndActiveGoals(Long userId, Model model);
}
