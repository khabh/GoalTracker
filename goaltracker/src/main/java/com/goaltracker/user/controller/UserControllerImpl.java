package com.goaltracker.user.controller;

import com.goaltracker.user.dto.UsernameDuplicationCheckDTO;
import com.goaltracker.user.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

@Controller
@RequestMapping("/goal-tracker/users")
public class UserControllerImpl implements UserController {

    private final UserService userService;

    @Autowired
    public UserControllerImpl(UserService userService) {
        this.userService = userService;
    }

    @Override
    @GetMapping("/username-check/{username}")
    public @ResponseBody ResponseEntity<UsernameDuplicationCheckDTO> checkUsernameDuplication(@PathVariable String username) {
        UsernameDuplicationCheckDTO usernameDuplicationCheckDTO = userService.checkUsernameDuplication(username);
        return ResponseEntity.ok(usernameDuplicationCheckDTO);
    }

    @Override
    @GetMapping("/me/active-goals")
    @PreAuthorize("hasAnyRole('USER')")
    public String redirectToUserActiveGoals() {
        String username = SecurityContextHolder.getContext().getAuthentication().getName();
        Long userId = userService.getUserIdByUsername(username);

        return createUserActiveGoalsRedirectURL(userId);
    }

    @Override
    public String showUserAndActiveGoals(Long userId) {
        return null;
    }

    private String createUserActiveGoalsRedirectURL(Long userId) {
        return String.format("redirect:/goal-tracker/users/%d/active-goals", userId);
    }
}
