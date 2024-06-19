package com.work.team5.controller;

import com.work.team5.dto.UserDto;
import com.work.team5.model.User;
import com.work.team5.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/users")
public class UserController {

    @Autowired
    private UserService userService;

    @GetMapping("/home")
    public String showHome(Model model, @AuthenticationPrincipal UserDetails userDetails) {
        if (userDetails != null) {
            User user = userService.getUserByUserid(userDetails.getUsername());
            if (user != null) {
                model.addAttribute("name", user.getName());
                return "home";  // Render the home page
            }
        }
        return "redirect:/users/login";  // Redirect to login if user details are not present
    }

    @GetMapping("/register")
    public String showRegistrationForm(Model model) {
        model.addAttribute("user", new UserDto());
        return "userAccount/register";
    }

    @GetMapping("/login")
    public String showLoginForm(Model model) {
        model.addAttribute("user", new UserDto());
        return "userAccount/login";
    }

    @PostMapping("/register")
    public String registerUser(@ModelAttribute("user") UserDto userDto, Model model) {
        User registeredUser = userService.registerUser(userDto);
        if (registeredUser != null) {
            model.addAttribute("registeredUserName", registeredUser.getName());
            model.addAttribute("registrationSuccess", true);
        } else {
            model.addAttribute("registrationFailure", true);
        }
        return "userAccount/login";
    }

    @PostMapping("/login")
    public String loginUser(@ModelAttribute("user") UserDto userDto, Model model, @AuthenticationPrincipal UserDetails userDetails) {
        if (userDetails != null) {
            User user = userService.getUserByUserid(userDetails.getUsername());
            if (user != null) {
                model.addAttribute("name", user.getName());
                return "redirect:/users/home";  // Redirect to the home page on successful login
            }
        }
        model.addAttribute("loginFailure", true);
        return "userAccount/login";  // Stay on the login page if login fails
    }
}
