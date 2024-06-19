package com.work.team5.service.meal;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.work.team5.dto.ChatGptResponse;
import com.work.team5.model.User;
import com.work.team5.model.meal.Meal;
import com.work.team5.model.meal.MealPlan;
import com.work.team5.model.meal.NutritionalInformation;
import com.work.team5.repository.meal.MealPlanRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class MealPlanService {

    @Autowired
    private MealPlanRepository mealPlanRepository;

    private final ObjectMapper objectMapper = new ObjectMapper();

    public MealPlan findLatestMealPlanByUser(User user) {
        return mealPlanRepository.findTopByUserOrderByIdDesc(user);
    }

    public MealPlan saveMealPlanFromResponse(ChatGptResponse response, User user) {
        try {
            if (response != null && !response.getChoices().isEmpty()) {
                String content = response.getChoices().get(0).getMessage().getContent();
                // Extract JSON from the response content
                String jsonContent = extractJsonFromContent(content);

                JsonNode root = objectMapper.readTree(jsonContent);

                MealPlan mealPlan = new MealPlan();
                mealPlan.setUser(user);

                JsonNode breakfastNode = root.path("breakfast");
                if (!breakfastNode.isMissingNode()) {
                    mealPlan.setBreakfast(parseMeal(breakfastNode));
                }

                JsonNode lunchNode = root.path("lunch");
                if (!lunchNode.isMissingNode()) {
                    mealPlan.setLunch(parseMeal(lunchNode));
                }

                JsonNode dinnerNode = root.path("dinner");
                if (!dinnerNode.isMissingNode()) {
                    mealPlan.setDinner(parseMeal(dinnerNode));
                }

                JsonNode totalNutritionalInfoNode = root.path("totalDailyNutritionalInfo");
                if (!totalNutritionalInfoNode.isMissingNode()) {
                    mealPlan.setTotalDailyNutritionalInfo(parseTotalNutritionalInformation(totalNutritionalInfoNode));
                }

                return mealPlanRepository.save(mealPlan);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

    private String extractJsonFromContent(String content) {
        int jsonStart = content.indexOf("{");
        int jsonEnd = content.lastIndexOf("}");
        if (jsonStart != -1 && jsonEnd != -1) {
            return content.substring(jsonStart, jsonEnd + 1);
        }
        throw new IllegalArgumentException("Invalid JSON format in content");
    }

    private Meal parseMeal(JsonNode mealNode) {
        Meal meal = new Meal();
        meal.setMealName(mealNode.path("mealName").asText());
        meal.setPortionSizes(mealNode.path("portionSizes").toString());
        meal.setNutritionalInformation(parseNutritionalInformation(mealNode.path("NutritionalInformation")));
        meal.setCaloricContent(mealNode.path("caloricContent").asInt());
        meal.setCookingMethod(mealNode.path("detailedCookingMethod").asText());
        return meal;
    }

    private NutritionalInformation parseNutritionalInformation(JsonNode nutritionalInfoNode) {
        NutritionalInformation nutritionalInfo = new NutritionalInformation();
        nutritionalInfo.setCarbs(nutritionalInfoNode.path("carbs").asInt());
        nutritionalInfo.setFat(nutritionalInfoNode.path("fat").asInt());
        nutritionalInfo.setProtein(nutritionalInfoNode.path("protein").asInt());
        return nutritionalInfo;
    }

    private NutritionalInformation parseTotalNutritionalInformation(JsonNode nutritionalInfoNode) {
        NutritionalInformation nutritionalInfo = new NutritionalInformation();
        nutritionalInfo.setCarbs(nutritionalInfoNode.path("total_carbs").asInt());
        nutritionalInfo.setFat(nutritionalInfoNode.path("total_fat").asInt());
        nutritionalInfo.setProtein(nutritionalInfoNode.path("total_protein").asInt());
        return nutritionalInfo;
    }
}
