package com.example.internintelligence_potfolioapidevelopment.dao.repo;

import com.example.internintelligence_potfolioapidevelopment.dao.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface UserRepo extends JpaRepository<User,Integer> {
    Optional<User> findByEmail(String email);

    Optional<User> findById(Integer id);

    boolean existsByEmail(String email);
}
