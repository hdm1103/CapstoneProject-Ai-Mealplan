package com.work.team5.model.meal;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;
import java.util.List;

@Embeddable
@Getter
@Setter
@NoArgsConstructor
public class Meal {

    private String mealName;

    @ElementCollection
    @CollectionTable(name = "meal_ingredients", joinColumns = @JoinColumn(name = "meal_id"))
    @Column(name = "ingredient")
    private List<String> ingredients;

    @Embedded
    private NutritionalInformation nutritionalInformation;

    private int caloricContent;
    private String cookingMethod;
    private String portionSizes;
}
