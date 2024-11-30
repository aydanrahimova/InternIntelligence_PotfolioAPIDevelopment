package com.example.internintelligence_potfolioapidevelopment.dao.repo;

import com.example.internintelligence_potfolioapidevelopment.dao.entity.Education;
import com.example.internintelligence_potfolioapidevelopment.dto.EducationDto;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface EducationRepo extends JpaRepository<Education,Integer> {

    Optional<Education> findByUserIdAndId(Integer userId, Integer educationId);
    List<Education> findAllByUserId(Integer userId);

    List<Education> findByUserId(Integer id);
}
