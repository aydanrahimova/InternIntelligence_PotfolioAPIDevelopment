package com.example.internintelligence_potfolioapidevelopment.service;

import com.example.internintelligence_potfolioapidevelopment.dao.entity.User;
import com.example.internintelligence_potfolioapidevelopment.dao.repo.*;
import com.example.internintelligence_potfolioapidevelopment.dto.request.ChangePasswordDto;
import com.example.internintelligence_potfolioapidevelopment.dto.request.UserDetailsRequest;
import com.example.internintelligence_potfolioapidevelopment.dto.response.UserDetailsResponse;
import com.example.internintelligence_potfolioapidevelopment.dto.response.UserResponse;
import com.example.internintelligence_potfolioapidevelopment.exception.ForbiddenException;
import com.example.internintelligence_potfolioapidevelopment.exception.IllegalArgumentException;
import com.example.internintelligence_potfolioapidevelopment.exception.ResourceNotFoundException;
import com.example.internintelligence_potfolioapidevelopment.mapper.*;
import com.example.internintelligence_potfolioapidevelopment.utility.JwtUtil;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
@Slf4j
@Transactional
public class UserService {

    private final UserRepo userRepo;
    private final UserMapper userMapper;
    private final JwtUtil jwtUtil;
    private final PasswordEncoder passwordEncoder;

    public UserDetailsResponse getById(Integer id) {
        log.info("Attempting to get profile of user with id {}", id);
        return userRepo.findById(id)
                .map(user -> {
                    log.info("Successfully retrieved user with id {}", id);
                    return userMapper.toDetailsResponse(user);
                })
                .orElseThrow(() -> {
                    log.warn("User with id {} not found", id);
                    return new ResourceNotFoundException("User with id " + id + " not found");
                });
    }

    public UserDetailsResponse editUserInfo(Integer id,HttpServletRequest http, UserDetailsRequest userDetailsRequest) {
        Integer currentUserId = extractUserIdFromToken(http);
        if(!id.equals(currentUserId)){
            log.info("User with id {} attempted to edit another user's profile.", currentUserId);
            throw new ForbiddenException("Forbidden access.");
        }
        log.info("Editing user operation is started...");
        User user = userRepo.findById(currentUserId).orElseThrow(() -> {
            log.warn("User with id {} not found", currentUserId);
            return new ResourceNotFoundException("USER_NOT_FOUND");
        });
        userMapper.mapForUpdate(user, userDetailsRequest);
        userRepo.save(user);
        log.info("User with ID {} successfully updated.", currentUserId);
        return userMapper.toDetailsResponse(user);
    }

    public void changePassword(Integer id,HttpServletRequest http, ChangePasswordDto changePasswordDto) {
        Integer currentUserId = extractUserIdFromToken(http);
        if(!id.equals(currentUserId)){
            log.info("User with id {} attempted to change password of another user's profile.", currentUserId);
            throw new ForbiddenException("Forbidden access.");
        }
        log.info("Changing password operation is started...");
        User user = userRepo.findById(currentUserId).orElseThrow(() -> {
            log.warn("User with id {} not found", currentUserId);
            return new ResourceNotFoundException("USER_NOT_FOUND");
        });
        if (changePasswordDto.getNewPassword().equals(changePasswordDto.getRetryPassword()) &&
                passwordEncoder.matches(changePasswordDto.getCurrentPassword(), user.getPassword())) {
            user.setPassword(passwordEncoder.encode(changePasswordDto.getNewPassword()));
            userRepo.save(user);
            log.info("Password for user with id {} changed.", currentUserId);
        } else {
            log.error("Failed to change password");
            throw new IllegalArgumentException("Old password entered incorrectly or new passwords do not match");
        }
    }

    public void deleteOwnProfile(HttpServletRequest http, Integer id){
        Integer currentUserId = extractUserIdFromToken(http);
        if(!id.equals(currentUserId)){
            log.info("User with id {} attempted to delete another user's profile.", currentUserId);
            throw new ForbiddenException("Forbidden access.");
        }
        log.info("Deleting process of user {} started...",currentUserId);
        userRepo.deleteById(currentUserId);
        log.info("User successfully deleted.");

    }

    private Integer extractUserIdFromToken(HttpServletRequest http) {
        return jwtUtil.getUserId(jwtUtil.resolveClaims(http));
    }

    //admin operations
    public List<UserResponse> getAll() {
        log.info("Attempting to get all users.");
        List<UserResponse> users = userRepo.findAll()
                .stream()
                .map(userMapper::toResponse)
                .toList();
        log.info("All users returned.");
        return users;
    }

    public void deleteUser(Integer userId) {
        log.info("Deleting process of user {} started by admin",userId);
        userRepo.deleteById(userId);
        log.info("User successfully deleted.");
    }

}
