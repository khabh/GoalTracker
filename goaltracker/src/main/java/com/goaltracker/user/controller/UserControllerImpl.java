package com.goaltracker.user.controller;

import com.goaltracker.auth.dto.InitialPasswordDTO;
import com.goaltracker.user.dto.CreateUserDTO;
import com.goaltracker.user.service.UserService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

@Controller
@RequestMapping("/goal-tracker/users")
public class UserControllerImpl implements UserController {

    private final UserService userService;

    @Autowired
    public UserControllerImpl(UserService userService) {
        this.userService = userService;
    }

    @Override
    @GetMapping("/create")
    public String showSignUpForm(Model model) {
        return "goaltracker/signUp";
    }

    @Override
    @PostMapping
    public String signUpUser(@Valid CreateUserDTO createUserDTO, BindingResult createUserResult,
                             @Valid InitialPasswordDTO initialPasswordDTO, BindingResult initialPasswordResult, RedirectAttributes redirectAttributes) {
        if (createUserResult.hasErrors() || initialPasswordResult.hasErrors()) {
            redirectAttributes.addFlashAttribute( BindingResult.MODEL_KEY_PREFIX + "createUserDTO", createUserResult);
            redirectAttributes.addFlashAttribute("createUserDTO", createUserDTO);

            redirectAttributes.addFlashAttribute( BindingResult.MODEL_KEY_PREFIX + "initialPasswordDTO", initialPasswordResult);
            redirectAttributes.addFlashAttribute("initialPasswordDTO", initialPasswordDTO);
            return "redirect:/goal-tracker/users/create";
        }
        return null;
    }
}
