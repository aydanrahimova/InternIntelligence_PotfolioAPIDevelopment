package com.example.internintelligence_potfolioapidevelopment.dao.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDate;

@Entity
@Table(name = "project")
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class Project {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String name;//255
    private String description;//2000
    private LocalDate startTime;//past
    private LocalDate endTime;//past
    private String projectUrl;
    @ManyToOne
    private User user;


}
