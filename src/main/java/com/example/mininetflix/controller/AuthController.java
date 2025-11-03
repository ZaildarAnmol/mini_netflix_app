package com.example.mininetflix.controller;

import com.example.mininetflix.dto.RegisterRequest;
import com.example.mininetflix.service.UserService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;

/**
 * Handles user authentication flows such as displaying the login form and
 * registering new accounts. The actual login processing is delegated to
 * Spring Security, while user creation is performed by {@link UserService}.
 */
@Controller
public class AuthController {

    @Autowired
    private UserService userService;

    /**
     * Show the login page. The actual authentication is handled by Spring Security.
     */
    @GetMapping("/login")
    public String login() {
        return "login";
    }

    /**
     * Show the registration form. Populates the model with a fresh
     * {@link RegisterRequest} so that Thymeleaf can bind form fields to it.
     */
    @GetMapping("/register")
    public String registerForm(Model model) {
        model.addAttribute("form", new RegisterRequest());
        return "register";
    }

    /**
     * Process a submitted registration form. Validation errors are returned back
     * to the form. On successful registration the user is redirected to the
     * login page with a query parameter indicating success.
     */
    @PostMapping("/register")
    public String register(@Valid @ModelAttribute("form") RegisterRequest form,
                           BindingResult result,
                           Model model) {
        if (result.hasErrors()) {
            return "register";
        }
        try {
            userService.register(form);
            return "redirect:/login?registered";
        } catch (IllegalArgumentException ex) {
            model.addAttribute("error", ex.getMessage());
            return "register";
        }
    }
}