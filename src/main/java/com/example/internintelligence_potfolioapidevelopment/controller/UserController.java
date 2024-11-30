package com.example.internintelligence_potfolioapidevelopment.controller;

import com.example.internintelligence_potfolioapidevelopment.dto.request.ChangePasswordDto;
import com.example.internintelligence_potfolioapidevelopment.dto.request.UserDetailsRequest;
import com.example.internintelligence_potfolioapidevelopment.dto.response.UserDetailsResponse;
import com.example.internintelligence_potfolioapidevelopment.dto.response.UserResponse;
import com.example.internintelligence_potfolioapidevelopment.service.UserService;
import jakarta.servlet.http.HttpServletRequest;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/user")
@RequiredArgsConstructor
public class UserController {

    private final UserService userService;

    @GetMapping("/{id}")
    public UserDetailsResponse getUserById(@PathVariable Integer id) {
        return userService.getById(id);
    }

    @PutMapping("/{id}/edit")
    public UserDetailsResponse editUserInfo(
            @PathVariable Integer id,
            HttpServletRequest http,
            @Validated @RequestBody UserDetailsRequest userDetailsRequest
    ) {
        return userService.editUserInfo(id,http, userDetailsRequest);
    }

    @PatchMapping("/{id}/change-password")
    public void changePassword(
            @PathVariable Integer id,
            HttpServletRequest http,
            @RequestBody ChangePasswordDto changePasswordDto
    ){
        userService.changePassword(id,http, changePasswordDto);
    }

    @ResponseStatus(HttpStatus.NO_CONTENT)
    @DeleteMapping("/{id}/delete")
    public void deleteOwnProfile(HttpServletRequest http,@PathVariable Integer id){
        userService.deleteOwnProfile(http,id);
    }

    @ResponseStatus(HttpStatus.NO_CONTENT)
    @DeleteMapping("/admin/delete-user/{userId}")
    public void deleteUser(@PathVariable Integer userId){
        userService.deleteUser(userId);
    }

    @GetMapping("/admin/users")
    public List<UserResponse> getAllUser() {
        return userService.getAll();
    }

}
