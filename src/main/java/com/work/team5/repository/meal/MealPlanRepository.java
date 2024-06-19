package com.work.team5.repository.meal;

import com.work.team5.model.User;
import com.work.team5.model.meal.MealPlan;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface MealPlanRepository extends JpaRepository<MealPlan, Long> {
    List<MealPlan> findByUserId(Long userId);

    MealPlan findTopByUserOrderByIdDesc(User user);
}
