package com.goaltracker.auth.controller;

import com.goaltracker.auth.dto.SignInDTO;
import com.goaltracker.auth.dto.UserSignUpDTO;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.validation.Valid;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

public interface AuthController {
    String showSignInForm();
    String signInUser(SignInDTO signInDTO, HttpServletResponse httpServletResponse);
    String showSignUpForm(Model model);
    ModelAndView signUpUser(@Valid UserSignUpDTO userSignUpDTO, BindingResult createUserResult, RedirectAttributes redirectAttributes, HttpServletResponse httpServletResponse);
}
