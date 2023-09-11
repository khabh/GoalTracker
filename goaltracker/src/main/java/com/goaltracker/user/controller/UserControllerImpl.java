package com.goaltracker.user.controller;

import com.goaltracker.user.dto.UsernameDuplicationCheckDTO;
import com.goaltracker.user.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
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
}
