package com.goaltracker.user.controller;

import com.goaltracker.user.dto.CreateUserDTO;
import jakarta.validation.Valid;
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

    @Override
    @GetMapping("/create")
    public String showSignUpForm(Model model) {
        if (!model.containsAttribute("createUserDTO")) {
            model.addAttribute("createUserDTO", new CreateUserDTO());
        }
        return "goaltracker/signUp";
    }

    @Override
    @PostMapping
    public String signUpUser(@Valid CreateUserDTO createUserDTO, BindingResult createUserResult, RedirectAttributes redirectAttributes) {
        if (createUserResult.hasErrors()) {
            redirectAttributes.addFlashAttribute( BindingResult.MODEL_KEY_PREFIX + "createUserDTO", createUserResult);
            redirectAttributes.addFlashAttribute("createUserDTO", createUserDTO);
            return "redirect:/goal-tracker/users/create";
        }
        return null;
    }
}
