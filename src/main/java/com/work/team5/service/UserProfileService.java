package com.work.team5.service;

import com.work.team5.model.User;
import com.work.team5.model.UserProfile;
import com.work.team5.repository.UserProfileRepository;
import com.work.team5.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class UserProfileService {

    @Autowired
    private UserProfileRepository userProfileRepository;

    @Autowired
    private UserRepository userRepository;

    public void saveUserProfile(UserProfile userProfile, String userId) {
        User user = userRepository.findByUserid(userId);
        if (user != null) {
            userProfile.setUser(user);
            userProfileRepository.save(userProfile);
        } else {
            throw new IllegalArgumentException("User not found with user_id: " + userId);
        }
    }

    public boolean hasUserProfile(String userId) {
        User user = userRepository.findByUserid(userId);
        if (user != null) {
            UserProfile userProfile = userProfileRepository.findByUser(user);
            return userProfile != null;
        }
        return false;
    }

    public UserProfile getUserProfileByUserId(String userId) {
        User user = userRepository.findByUserid(userId);
        if (user != null) {
            return userProfileRepository.findByUser(user);
        } else {
            throw new IllegalArgumentException("User not found with user_id: " + userId);
        }
    }

    public void updateUserProfile(UserProfile updatedProfile, String userId) {
        User user = userRepository.findByUserid(userId);
        if (user != null) {
            UserProfile existingProfile = userProfileRepository.findByUser(user);
            if (existingProfile != null) {
                existingProfile.setGender(updatedProfile.getGender());
                existingProfile.setAge(updatedProfile.getAge());
                existingProfile.setHeight(updatedProfile.getHeight());
                existingProfile.setWeight(updatedProfile.getWeight());
                existingProfile.setHealthConditions(updatedProfile.getHealthConditions());
                existingProfile.setMealPattern(updatedProfile.getMealPattern());
                existingProfile.setFoodPreferences(updatedProfile.getFoodPreferences());
                existingProfile.setExerciseFrequency(updatedProfile.getExerciseFrequency());
                existingProfile.setExerciseType(updatedProfile.getExerciseType());
                existingProfile.setHealthGoal(updatedProfile.getHealthGoal());

                userProfileRepository.save(existingProfile);
            } else {
                throw new IllegalArgumentException("UserProfile not found for user_id: " + userId);
            }
        } else {
            throw new IllegalArgumentException("User not found with user_id: " + userId);
        }
    }
}
