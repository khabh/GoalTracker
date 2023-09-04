package com.goaltracker.user.controller;

import com.goaltracker.auth.dto.InitialPasswordDTO;
import com.goaltracker.user.dto.CreateUserDTO;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

public interface UserController {
    String showSignUpForm(Model model);
    String signUpUser(CreateUserDTO createUserDTO, BindingResult createUserResult,
                      InitialPasswordDTO initialPasswordDTO, BindingResult initialPasswordResult,
                      RedirectAttributes redirectAttributes, HttpServletResponse httpServletResponse);
}
