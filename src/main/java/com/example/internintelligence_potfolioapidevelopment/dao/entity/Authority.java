package com.example.internintelligence_potfolioapidevelopment.dao.entity;

import com.example.internintelligence_potfolioapidevelopment.config.enums.Role;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

@Entity
@Table(name = "authority")
@Getter
@Setter
public class Authority {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;
    @Enumerated(EnumType.STRING)
    private Role name;
    @ManyToOne
    @JoinColumn(name = "user_id", nullable = false)
    private User user;

}
