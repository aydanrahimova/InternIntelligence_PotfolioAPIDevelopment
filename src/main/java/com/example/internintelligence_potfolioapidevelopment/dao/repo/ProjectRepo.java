package com.example.internintelligence_potfolioapidevelopment.dao.repo;

import com.example.internintelligence_potfolioapidevelopment.dao.entity.Project;
import org.springframework.data.domain.Page;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface ProjectRepo extends JpaRepository<Project,Integer> {
    Optional<Project> findByUserIdAndId(Integer userId, Integer projectId);
    List<Project> findAllByUserId(Integer userId);
}
