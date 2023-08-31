package com.goaltracker.user.controller;

import com.goaltracker.user.dto.CreateUserDTO;
import jakarta.validation.Valid;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

public interface UserController {
    String showSignUpForm(Model model);
    String signUpUser(@Valid CreateUserDTO createUserDTO, BindingResult result, RedirectAttributes redirectAttributes);
}
