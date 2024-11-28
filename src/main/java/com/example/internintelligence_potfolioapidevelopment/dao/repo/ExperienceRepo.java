package com.example.internintelligence_potfolioapidevelopment.dao.repo;

import com.example.internintelligence_potfolioapidevelopment.dao.entity.Experience;
import com.example.internintelligence_potfolioapidevelopment.dto.ExperienceDto;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface ExperienceRepo extends JpaRepository<Experience,Long> {
    List<Experience> findAllByUserId(Long userId);

    List<Experience> findByUserId(Long id);
}