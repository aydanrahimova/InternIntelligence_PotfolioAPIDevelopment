package com.example.internintelligence_potfolioapidevelopment.enums;

import com.example.internintelligence_potfolioapidevelopment.exception.IllegalArgumentException;
import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonFormat;

@JsonFormat(shape = JsonFormat.Shape.STRING)
public enum SkillLevel {
    BEGINNER,
    INTERMEDIATE,
    ADVANCED,
    EXPERT;

    @JsonCreator
    public static SkillLevel fromValue(String value) {
        for (SkillLevel level : values()) {
            if (level.name().equalsIgnoreCase(value)) {
                return level;
            }
        }
        throw new IllegalArgumentException("Invalid skill level:" + value);
    }
}
