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
    private Integer id;
    @Column(nullable = false)
    private String name;
    private String description;
    private LocalDate startTime;
    private LocalDate endTime;
    private String projectUrl;
    @ManyToOne
    @JoinColumn(name = "user_id", nullable = false)
    private User user;


}
