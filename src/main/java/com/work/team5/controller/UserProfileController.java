package com.work.team5.controller;

import com.work.team5.model.UserProfile;
import com.work.team5.service.UserProfileService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/profile")
public class UserProfileController {

    @Autowired
    private UserProfileService userProfileService;

    @GetMapping("/view")
    public String viewProfile(Model model, Authentication authentication) {
        String userId = authentication.getName();
        UserProfile userProfile = userProfileService.getUserProfileByUserId(userId);
        model.addAttribute("userProfile", userProfile);
        return "userProfile/viewProfile";
    }

    @GetMapping("/create")
    public String showCreateProfileForm(Model model, Authentication authentication) {
        model.addAttribute("userProfile", new UserProfile());
        return "userProfile/createProfile";
    }

    @PostMapping("/create")
    public String createProfile(@ModelAttribute UserProfile userProfile, Authentication authentication) {
        String userId = authentication.getName();
        userProfileService.saveUserProfile(userProfile, userId);
        return "redirect:/profile/view";
    }

    @GetMapping("/edit")
    public String showEditProfileForm(Model model, Authentication authentication) {
        String userId = authentication.getName();
        UserProfile userProfile = userProfileService.getUserProfileByUserId(userId);
        model.addAttribute("userProfile", userProfile);
        return "userProfile/editProfile";
    }

    @PostMapping("/edit")
    public String updateProfile(@ModelAttribute UserProfile userProfile, Authentication authentication) {
        String userId = authentication.getName();
        userProfileService.updateUserProfile(userProfile, userId);
        return "redirect:/profile/view";
    }
}
