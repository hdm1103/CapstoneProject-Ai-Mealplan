package com.work.team5.model.meal;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.Embeddable;

@Embeddable
@Getter
@Setter
@NoArgsConstructor
public class NutritionalInformation {

    private int carbs;
    private int fat;
    private int protein;
}
