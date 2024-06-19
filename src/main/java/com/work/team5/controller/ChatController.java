package com.work.team5.controller;

import com.work.team5.dto.ChatGptResponse;
import com.work.team5.model.User;
import com.work.team5.model.UserProfile;
import com.work.team5.model.meal.MealPlan;
import com.work.team5.service.ChatGptService;
import com.work.team5.service.UserProfileService;
import com.work.team5.service.UserService;
import com.work.team5.service.meal.MealPlanService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

@Controller
@RequestMapping("/bot")
public class ChatController {

    @Autowired
    private ChatGptService chatGptService;

    @Autowired
    private UserService userService;

    @Autowired
    private UserProfileService userProfileService;

    @Autowired
    private MealPlanService mealPlanService;

    @GetMapping("/chat")
    public String getChatPage(@RequestParam(name = "showProfile", required = false) Boolean showProfile,
                              Model model) {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        if (authentication == null || !authentication.isAuthenticated()) {
            throw new IllegalArgumentException("Authentication is required");
        }

        String userid = authentication.getName(); // assuming userid is used as the principal name in authentication
        User user = userService.getUserByUserid(userid);

        if (user == null) {
            throw new IllegalArgumentException("User not found with userid: " + userid);
        }

        model.addAttribute("user_name", user.getName());

        UserProfile userProfile = userProfileService.getUserProfileByUserId(userid);
        if (userProfile == null) {
            throw new IllegalArgumentException("UserProfile not found for user: " + userid);
        }

        if (Boolean.TRUE.equals(showProfile)) {
            model.addAttribute("userProfile", userProfile);
        }

        String formattedPrompt = formatPromptWithUserProfile(userProfile);
        ChatGptResponse chatGptResponse = chatGptService.getChatGptResponse(formattedPrompt);
        if (chatGptResponse != null) {
            String responseText = chatGptResponse.getChoices().get(0).getMessage().getContent();
            model.addAttribute("response", responseText);

            // Save the meal plan from the response
            mealPlanService.saveMealPlanFromResponse(chatGptResponse, user); // Pass user to saveMealPlanFromResponse
        }

        return "gpt/chat";
    }

    private String formatPromptWithUserProfile(UserProfile userProfile) {
        StringBuilder sb = new StringBuilder();
        sb.append("User Profile:\n");
        sb.append("Name: ").append(userProfile.getUser().getName()).append("\n");
        sb.append("Gender: ").append(userProfile.getGender()).append("\n");
        sb.append("Age: ").append(userProfile.getAge()).append("\n");
        sb.append("Height: ").append(userProfile.getHeight()).append("\n");
        sb.append("Weight: ").append(userProfile.getWeight()).append("\n");
        sb.append("Health Conditions: ").append(userProfile.getHealthConditions()).append("\n");
        sb.append("Meal Pattern: ").append(userProfile.getMealPattern()).append("\n");
        sb.append("Food Preferences: ").append(userProfile.getFoodPreferences()).append("\n");
        sb.append("Exercise Frequency: ").append(userProfile.getExerciseFrequency()).append("\n");
        sb.append("Exercise Type: ").append(userProfile.getExerciseType()).append("\n");
        sb.append("Health Goal: ").append(userProfile.getHealthGoal()).append("\n");
        sb.append("\nPlease provide only the diet information based on the user's profile. Create a customized diet by referring to the user's profile. Serve it in Korean. Divide the information into breakfast, lunch, and dinner. For each meal, provide details such as mealName, caloricContent, detailed cookingMethod, portionSizes, NutritionalInformation.carbs, NutritionalInformation.fat, and NutritionalInformation.protein. Additionally, provide totalDailyNutritionalInfo (total_carbs, total_fat, total_protein). Please provide the data in JSON format.\n");

        return sb.toString();
    }

    @GetMapping("/mealPlan")
    public String getMealPlanPage(Model model) {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        if (authentication == null || !authentication.isAuthenticated()) {
            throw new IllegalArgumentException("Authentication is required");
        }

        String userId = authentication.getName();
        User user = userService.getUserByUserid(userId);

        if (user == null) {
            throw new IllegalArgumentException("User not found with userid: " + userId);
        }

        MealPlan mealPlan = mealPlanService.findLatestMealPlanByUser(user);

        if (mealPlan == null) {
            throw new IllegalArgumentException("MealPlan not found for user: " + userId);
        }

        model.addAttribute("user_name", user.getName());
        model.addAttribute("mealPlan", mealPlan);

        return "gpt/mealResponse";
    }
}
