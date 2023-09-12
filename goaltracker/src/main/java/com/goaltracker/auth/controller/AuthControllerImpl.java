package com.goaltracker.auth.controller;

import com.goaltracker.auth.dto.SignInDTO;
import com.goaltracker.auth.dto.UserSignUpDTO;
import com.goaltracker.auth.service.UserCredentialService;
import com.goaltracker.config.Constants;
import com.goaltracker.user.exception.EmailDuplicatedException;
import com.goaltracker.user.exception.UserNotFoundException;
import com.goaltracker.user.exception.UsernameDuplicatedException;
import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;
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
        return "goalTracker/signIn";
    }

    @Override
    @PostMapping("/sign-in")
    public ResponseEntity<String> signInUser(@Valid SignInDTO signInDTO, HttpServletResponse httpServletResponse) {
        try {
            String token = userCredentialService.authenticateUserWithJwtToken(signInDTO);
            addTokenCookieToResponse(token, httpServletResponse);

            return ResponseEntity.ok("로그인 성공");
        } catch (BadCredentialsException | UserNotFoundException e)  {
            return ResponseEntity
                    .status(HttpStatus.UNAUTHORIZED.value())
                    .build();
        }
    }

    @Override
    @GetMapping("/sign-up")
    public String showSignUpForm(Model model) {
        if (model.getAttribute("userSignUpDTO") == null)
            model.addAttribute(new UserSignUpDTO());
        return "goalTracker/signUp";
    }

    @Override
    @PostMapping("sign-up")
    public ModelAndView signUpUser(@Valid UserSignUpDTO userSignUpDTO, BindingResult signUpValidationResult,
                             RedirectAttributes redirectAttributes, HttpServletResponse httpServletResponse) {
        if (signUpValidationResult.hasErrors()) {
            redirectAttributes.addFlashAttribute( BindingResult.MODEL_KEY_PREFIX + "userSignUpDTO", signUpValidationResult);
            redirectAttributes.addFlashAttribute("userSignUpDTO", userSignUpDTO);

            return new ModelAndView("redirect:/goal-tracker/auth/sign-up");
        }
        try {
            userCredentialService.signUpUser(userSignUpDTO);

            return new ModelAndView("redirect:/goal-tracker/auth/sign-in");
        } catch (UsernameDuplicatedException | EmailDuplicatedException exception) {
            return handleSignUpException(exception, signUpValidationResult, redirectAttributes, userSignUpDTO);
        }
    }

    private ModelAndView handleSignUpException(Exception exception, BindingResult signUpValidationResult,
                                               RedirectAttributes redirectAttributes, UserSignUpDTO userSignUpDTO) {
        String fieldErrorKey;
        if (exception instanceof UsernameDuplicatedException) {
            fieldErrorKey = "username";
        } else if (exception instanceof EmailDuplicatedException) {
            fieldErrorKey = "email";
        } else {
            throw new IllegalArgumentException("Unsupported exception type: " + exception.getClass().getSimpleName());
        }

        signUpValidationResult.rejectValue(fieldErrorKey, "error.userSignUpDTO." + fieldErrorKey, exception.getMessage());

        redirectAttributes.addFlashAttribute(BindingResult.MODEL_KEY_PREFIX + "userSignUpDTO", signUpValidationResult);
        redirectAttributes.addFlashAttribute("userSignUpDTO", userSignUpDTO);

        return new ModelAndView("redirect:/goal-tracker/auth/sign-up");
    }

    private void addTokenCookieToResponse(String token, HttpServletResponse httpServletResponse) {
        Cookie cookie = new Cookie(Constants.AUTHORIZATION_HEADER, token);
        cookie.setPath("/");
        cookie.setDomain("");
        cookie.setHttpOnly(true);
        cookie.setSecure(true);
        cookie.setMaxAge(7 * 24 * 60 * 60);
        httpServletResponse.addCookie(cookie);
    }
}
