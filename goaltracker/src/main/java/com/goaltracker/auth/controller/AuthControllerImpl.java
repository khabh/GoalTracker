package com.goaltracker.auth.controller;

import com.goaltracker.auth.dto.SignInDTO;
import com.goaltracker.auth.dto.UserSignUpDTO;
import com.goaltracker.auth.service.UserCredentialService;
import com.goaltracker.config.Constants;
import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServletResponse;
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
@RequestMapping("/goal-tracker/auth")
public class AuthControllerImpl implements AuthController {

    private final UserCredentialService userCredentialService;

    @Autowired
    public AuthControllerImpl(UserCredentialService userCredentialService) {
        this.userCredentialService = userCredentialService;
    }

    @Override
    @GetMapping("/sign-in")
    public String showSignInForm() {
        return "goaltracker/signIn";
    }

    @Override
    @PostMapping("/sign-in")
    public String signInUser(@Valid SignInDTO signInDTO, HttpServletResponse httpServletResponse) {
        String token = userCredentialService.authenticateUserWithJwtToken(signInDTO);
        addTokenCookieToResponse(token, httpServletResponse);

        return null;
    }

    @Override
    @GetMapping("/sign-up")
    public String showSignUpForm(Model model) {
        if (model.getAttribute("userSignUpDTO") == null)
            model.addAttribute(new UserSignUpDTO());
        return "goaltracker/signUp";
    }

    @Override
    @PostMapping("sign-up")
    public String signUpUser(@Valid UserSignUpDTO userSignUpDTO, BindingResult signUpValidationResult,
                             RedirectAttributes redirectAttributes, HttpServletResponse httpServletResponse) {
        if (signUpValidationResult.hasErrors()) {
            redirectAttributes.addFlashAttribute( BindingResult.MODEL_KEY_PREFIX + "userSignUpDTO", signUpValidationResult);
            redirectAttributes.addFlashAttribute("userSignUpDTO", userSignUpDTO);

            return "redirect:/goal-tracker/auth/sign-up";
        }
        String token = userCredentialService.signUpAndAuthenticateUser(userSignUpDTO);
        addTokenCookieToResponse(token, httpServletResponse);

        return null;
    }

    private void addTokenCookieToResponse(String token, HttpServletResponse httpServletResponse) {
        Cookie cookie = new Cookie(Constants.AUTHORIZATION_HEADER, token);
        cookie.setMaxAge(7 * 24 * 60 * 60);
        httpServletResponse.addCookie(cookie);
    }
}
