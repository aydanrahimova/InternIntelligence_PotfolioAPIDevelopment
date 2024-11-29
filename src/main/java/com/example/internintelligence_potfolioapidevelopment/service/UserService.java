package com.example.internintelligence_potfolioapidevelopment.service;

import com.example.internintelligence_potfolioapidevelopment.dao.entity.User;
import com.example.internintelligence_potfolioapidevelopment.dao.repo.*;
import com.example.internintelligence_potfolioapidevelopment.dto.*;
import com.example.internintelligence_potfolioapidevelopment.dto.request.ChangePasswordDto;
import com.example.internintelligence_potfolioapidevelopment.dto.request.UserDetailsRequestDto;
import com.example.internintelligence_potfolioapidevelopment.exception.IllegalArgumentException;
import com.example.internintelligence_potfolioapidevelopment.exception.ResourceNotFoundException;
import com.example.internintelligence_potfolioapidevelopment.mapper.*;
import com.example.internintelligence_potfolioapidevelopment.utility.JwtUtil;
import jakarta.servlet.http.HttpServletRequest;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
@Slf4j
public class UserService {

    private final UserRepo userRepo;
    private final UserMapper userMapper;
    private final JwtUtil jwtUtil;
    private final PasswordEncoder passwordEncoder;

    public UserDto getOwnProfile(HttpServletRequest http) {
        Long userId = extractUserIdFromToken(http);
        log.info("Getting own profile operation is started.");
        User user = userRepo.findById(userId).orElseThrow(() -> {
            log.warn("User with id {} not found.", userId);
            return new ResourceNotFoundException("USER_NOT_FOUND");
        });
        log.info("User profile returned.");
        return userMapper.toDto(user);
    }

    public UserDto getById(Long id) {
        log.info("Attempting to get profile of user with id {}", id);

        return userRepo.findById(id).map(user -> {
            log.info("Successfully retrieved user with id {}", id);
            return userMapper.toDto(user);
        }).orElseThrow(() -> {
            log.warn("User with id {} not found", id);
            return new ResourceNotFoundException("User with id " + id + " not found");
        });
    }

    public UserDto editUserInfo(HttpServletRequest http, UserDetailsRequestDto userDetailsRequestDto) {
        Long userId = extractUserIdFromToken(http);
        log.info("Editing user operation is started...");
        User user = userRepo.findById(userId).orElseThrow(() -> {
            log.warn("User with id {} not found", userId);
            return new ResourceNotFoundException("USER_NOT_FOUND");
        });
        userMapper.mapForUpdate(user, userDetailsRequestDto);
        userRepo.save(user);
        log.info("User with ID {} successfully updated.", userId);
        return userMapper.toDto(user);
    }

    public void changePassword(HttpServletRequest http, ChangePasswordDto changePasswordDto) {
        Long userId = extractUserIdFromToken(http);
        log.info("Changing password operation is started...");
        User user = userRepo.findById(userId).orElseThrow(() -> {
            log.warn("User with id {} not found", userId);
            return new ResourceNotFoundException("USER_NOT_FOUND");
        });
        if (changePasswordDto.getNewPassword().equals(changePasswordDto.getRetryPassword()) &&
                passwordEncoder.matches(changePasswordDto.getCurrentPassword(), user.getPassword())) {
            user.setPassword(passwordEncoder.encode(changePasswordDto.getNewPassword()));
            userRepo.save(user);
            log.info("Password for user with id {} changed.", userId);
        } else {
            log.error("Failed to change password");
            throw new IllegalArgumentException("Old password entered incorrectly or new passwords do not match");
        }
    }

    public List<UserDto> getAll() {
        log.info("Attempting to get all users.");
        List<UserDto> users = userRepo.findAll()
                .stream()
                .map(userMapper::toDto)
                .toList();
        log.info("All users returned.");
        return users;
    }

    private Long extractUserIdFromToken(HttpServletRequest http) {
        return jwtUtil.getUserId(jwtUtil.resolveClaims(http));
    }

    private void checkForUserExistence(Long id) {
        log.info("Checking for existence of user with id {}", id);
        if (!userRepo.existsById(id)) {
            log.warn("User with id {} not found", id);
            throw new ResourceNotFoundException("User with id " + id + " not found");
        }
    }

    public void deleteUser(Long userId) {
    }

    public void deleteOwnProfile() {
    }
}
