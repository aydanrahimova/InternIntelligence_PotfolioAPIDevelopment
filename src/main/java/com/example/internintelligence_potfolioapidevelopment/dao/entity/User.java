package com.example.internintelligence_potfolioapidevelopment.dao.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDate;
import java.util.List;

@Entity
@Table(name = "users")
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class User {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String firstName;
    private String lastName;
    private String email;
    private String password;
    private String title;
    private String about;
    private String phone;
    private LocalDate birthDay;
    @OneToMany
    private List<Education> education;
    @ManyToMany
    private List<Skill> skills;
    @OneToMany
    private List<Experience> experiences;
    @OneToMany
    private List<Project> projects;

    @OneToMany(mappedBy = "user")
    private List<Authority> authorities;
}
