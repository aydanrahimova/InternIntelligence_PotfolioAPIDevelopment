package com.example.internintelligence_potfolioapidevelopment.dao.repo;

import com.example.internintelligence_potfolioapidevelopment.dao.entity.Project;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface ProjectRepo extends JpaRepository<Project,Integer> {
    List<Project> findAllByUserId(Integer userId);
}
