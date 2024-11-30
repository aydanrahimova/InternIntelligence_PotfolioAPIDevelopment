package com.example.internintelligence_potfolioapidevelopment.dao.repo;

import com.example.internintelligence_potfolioapidevelopment.dao.entity.Education;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface EducationRepo extends JpaRepository<Education,Integer> {

    List<Education> findAllByUserId(Integer userId);

}
