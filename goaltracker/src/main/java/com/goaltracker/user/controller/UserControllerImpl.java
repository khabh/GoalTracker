package com.goaltracker.user.controller;

import com.goaltracker.user.dto.CreateFollowRelationDTO;
import com.goaltracker.user.dto.UserWithRelationDTO;
import com.goaltracker.user.dto.UsernameDuplicationCheckDTO;
import com.goaltracker.user.exception.follow.InvalidFollowActionException;
import com.goaltracker.user.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Controller
@RequestMapping("/goal-tracker")
public class UserControllerImpl implements UserController {

    private final UserService userService;

    @Autowired
    public UserControllerImpl(UserService userService) {
        this.userService = userService;
    }

    @Override
    @GetMapping("/users/username-check/{username}")
    public @ResponseBody ResponseEntity<UsernameDuplicationCheckDTO> checkUsernameDuplication(@PathVariable String username) {
        UsernameDuplicationCheckDTO usernameDuplicationCheckDTO = userService.checkUsernameDuplication(username);
        return ResponseEntity.ok(usernameDuplicationCheckDTO);
    }

    @Override
    @GetMapping("/users/me/active-goals")
    @PreAuthorize("hasAnyRole('USER')")
    public String redirectToUserActiveGoals() {
        String username = getLoggedInUsername();
        Long userId = userService.getUserIdByUsername(username);

        return createUserActiveGoalsRedirectURL(userId);
    }

    @Override
    @GetMapping("/users/{userId}/active-goals")
    public String showUserAndActiveGoals(@PathVariable("userId") Long userId, Model model) {
        Authentication userAuthentication = SecurityContextHolder.getContext().getAuthentication();
        model.addAttribute("userProfile", userService.getUserProfileWithFollowStats(userId, userAuthentication));
        model.addAttribute("activeGoals", userService.getUserActiveGoals(userId));

        return "goalTracker/userActiveGoals";
    }

    @Override
    @PostMapping("/follows")
    public ResponseEntity<Void> followUser(CreateFollowRelationDTO createFollowRelationDTO) {
        String loggedInUsername = getLoggedInUsername();
        userService.followNewUser(createFollowRelationDTO, loggedInUsername);

        return ResponseEntity.ok().build();
    }

    @Override
    @DeleteMapping("/users/{followeeId}/follows")
    public ResponseEntity<Void> unfollowUser(@PathVariable("followeeId") Long followeeId) {
        String loggedInUsername = getLoggedInUsername();
        userService.unfollowUser(followeeId, loggedInUsername);

        return ResponseEntity.ok().build();
    }

    @Override
    @GetMapping("/users/{userId}/followers")
    public String showFollowersOfUser(@PathVariable("userId") Long userId, Model model) {
        String loggedInUsername = getLoggedInUsername();
        List<UserWithRelationDTO> userFollowers = userService.getUserFollowers(userId, loggedInUsername);
        model.addAttribute("userProfiles", userFollowers);

        return "goalTracker/connectedUsers";
    }

    @ExceptionHandler(InvalidFollowActionException.class)
    ResponseEntity<Map<String, String>> handleInvalidFollowActionException(InvalidFollowActionException followActionException) {
        Map<String, String> error = new HashMap<>();
        error.put("message", followActionException.getMessage());

        return ResponseEntity.badRequest().body(error);
    }

    private static String getLoggedInUsername() {
        return SecurityContextHolder.getContext().getAuthentication().getName();
    }

    private String createUserActiveGoalsRedirectURL(Long userId) {
        return String.format("redirect:/goal-tracker/users/%d/active-goals", userId);
    }
}
