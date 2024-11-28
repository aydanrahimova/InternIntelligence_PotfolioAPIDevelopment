package com.example.internintelligence_potfolioapidevelopment.dao.repo;

import com.example.internintelligence_potfolioapidevelopment.dao.entity.Education;
import com.example.internintelligence_potfolioapidevelopment.dto.EducationDto;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface EducationRepo extends JpaRepository<Education,Long> {

    Optional<Education> findByUserIdAndId(Long userId, Long educationId);
    List<Education> findAllByUserId(Long userId);

    List<Education> findByUserId(Long id);
}
