package com.example.internintelligence_potfolioapidevelopment.controller;

import com.example.internintelligence_potfolioapidevelopment.dto.*;
import com.example.internintelligence_potfolioapidevelopment.dto.request.ChangePasswordDto;
import com.example.internintelligence_potfolioapidevelopment.dto.request.UserDetailsRequestDto;
import com.example.internintelligence_potfolioapidevelopment.service.UserService;
import io.swagger.v3.oas.annotations.Operation;
import jakarta.servlet.http.HttpServletRequest;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/users")
@RequiredArgsConstructor
public class UserController {

    private final UserService userService;

    @Operation(summary = "Retrieve another user's profile by their ID")
    @GetMapping("/{id}")
    public UserDto getUserById(@PathVariable Long id) {
        return userService.getById(id);
    }

    @Operation(summary = "Retrieve the authenticated user's profile")
    @GetMapping("/me")
    public UserDto getOwnProfile(HttpServletRequest http) {
        return userService.getOwnProfile(http);
    }

    @PutMapping("/edit")
    public UserDto editUserInfo(HttpServletRequest http, @RequestBody UserDetailsRequestDto userDetailsRequestDto) {
        return userService.editUserInfo(http, userDetailsRequestDto);
    }

    @PatchMapping("/change-password")
    public void changePassword(HttpServletRequest http, @RequestBody ChangePasswordDto changePasswordDto) {
        userService.changePassword(http, changePasswordDto);
    }

    @GetMapping("/admin/users")
    public List<UserDto> getAllUser() {
        return userService.getAll();
    }

    @DeleteMapping("/delete")
    public void deleteOwnProfile(){
        userService.deleteOwnProfile();
    }

    @DeleteMapping("/admin/delete-user/{userId}")
    public void deleteUser(@PathVariable Long userId){
        userService.deleteUser(userId);
    }

}
