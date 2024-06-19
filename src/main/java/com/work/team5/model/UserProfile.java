package com.work.team5.model;

import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;

@Entity
@Table(name = "userprofile")
@Getter
@Setter
public class UserProfile {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    // 성별
    @Column(nullable = false)
    private String gender;

    // 나이
    @Column(nullable = false)
    private int age=1;

    // 신장 (cm)
    @Column(nullable = false)
    private double height;

    // 체중 (kg)
    @Column(nullable = false)
    private double weight;

    // 기타 건강 상태
    @Column(nullable = false)
    private String healthConditions;

    // 식사 패턴
    @Column(nullable = false)
    private String mealPattern;

    // 식사 선호도
    @Column(nullable = false)
    private String foodPreferences;

    // 운동 빈도 (예: 주 3회)
    @Column(nullable = false)
    private String exerciseFrequency = "";

    // 운동 종류 (예: 유산소, 근력 운동)
    @Column(nullable = false)
    private String exerciseType;

    // 특정 건강 목표 (예: 혈당 조절)
    @Column(nullable = false)
    private String healthGoal;

    // User와의 관계 설정 (1:1 관계)
    @OneToOne
    @JoinColumn(name = "user_id", referencedColumnName = "id")
    private User user;
}
