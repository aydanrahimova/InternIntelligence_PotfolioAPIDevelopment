package com.example.internintelligence_potfolioapidevelopment.dao.entity;

import com.example.internintelligence_potfolioapidevelopment.enums.SkillLevel;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Table(name = "skill")
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class Skill {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String name;
    private SkillLevel level;
    @ManyToOne
    @JoinColumn(name = "user_id", nullable = false)
    private User user;

}
