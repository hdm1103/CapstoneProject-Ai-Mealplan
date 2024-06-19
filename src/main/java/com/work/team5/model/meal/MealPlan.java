package com.work.team5.model.meal;

import com.work.team5.model.User;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;

@Entity
@Table(name = "meal_plan")
@Getter
@Setter
@NoArgsConstructor
public class MealPlan {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    @JoinColumn(name = "user_id", nullable = false)
    private User user;

    @Embedded
    @AttributeOverrides({
            @AttributeOverride(name = "mealName", column = @Column(name = "breakfast_meal_name")),
            @AttributeOverride(name = "caloricContent", column = @Column(name = "breakfast_caloric_content")),
            @AttributeOverride(name = "cookingMethod", column = @Column(name = "breakfast_cooking_method")),
            @AttributeOverride(name = "portionSizes", column = @Column(name = "breakfast_portion_sizes")),
            @AttributeOverride(name = "nutritionalInformation.carbs", column = @Column(name = "breakfast_carbs")),
            @AttributeOverride(name = "nutritionalInformation.fat", column = @Column(name = "breakfast_fat")),
            @AttributeOverride(name = "nutritionalInformation.protein", column = @Column(name = "breakfast_protein"))
    })
    private Meal breakfast;

    @Embedded
    @AttributeOverrides({
            @AttributeOverride(name = "mealName", column = @Column(name = "lunch_meal_name")),
            @AttributeOverride(name = "caloricContent", column = @Column(name = "lunch_caloric_content")),
            @AttributeOverride(name = "cookingMethod", column = @Column(name = "lunch_cooking_method")),
            @AttributeOverride(name = "portionSizes", column = @Column(name = "lunch_portion_sizes")),
            @AttributeOverride(name = "nutritionalInformation.carbs", column = @Column(name = "lunch_carbs")),
            @AttributeOverride(name = "nutritionalInformation.fat", column = @Column(name = "lunch_fat")),
            @AttributeOverride(name = "nutritionalInformation.protein", column = @Column(name = "lunch_protein"))
    })
    private Meal lunch;

    @Embedded
    @AttributeOverrides({
            @AttributeOverride(name = "mealName", column = @Column(name = "dinner_meal_name")),
            @AttributeOverride(name = "caloricContent", column = @Column(name = "dinner_caloric_content")),
            @AttributeOverride(name = "cookingMethod", column = @Column(name = "dinner_cooking_method")),
            @AttributeOverride(name = "portionSizes", column = @Column(name = "dinner_portion_sizes")),
            @AttributeOverride(name = "nutritionalInformation.carbs", column = @Column(name = "dinner_carbs")),
            @AttributeOverride(name = "nutritionalInformation.fat", column = @Column(name = "dinner_fat")),
            @AttributeOverride(name = "nutritionalInformation.protein", column = @Column(name = "dinner_protein"))
    })
    private Meal dinner;

    @Embedded
    @AttributeOverrides({
            @AttributeOverride(name = "carbs", column = @Column(name = "total_carbs")),
            @AttributeOverride(name = "fat", column = @Column(name = "total_fat")),
            @AttributeOverride(name = "protein", column = @Column(name = "total_protein"))
    })
    private NutritionalInformation totalDailyNutritionalInfo;
}
