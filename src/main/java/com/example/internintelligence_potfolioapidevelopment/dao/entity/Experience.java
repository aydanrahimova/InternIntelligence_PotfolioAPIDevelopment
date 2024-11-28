package com.example.internintelligence_potfolioapidevelopment.dao.entity;

import com.example.internintelligence_potfolioapidevelopment.enums.EmploymentType;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDate;

@Entity
@Table(name = "experience")
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class Experience {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String title;
    private String description;
    private String organizationName;
    @Enumerated(EnumType.STRING)
    private EmploymentType employmentType;
    private LocalDate startTime;
    private LocalDate endTime;
    @ManyToOne
    private User user;
}