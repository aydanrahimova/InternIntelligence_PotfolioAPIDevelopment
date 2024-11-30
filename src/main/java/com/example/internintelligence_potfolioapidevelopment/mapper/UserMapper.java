package com.example.internintelligence_potfolioapidevelopment.mapper;

import com.example.internintelligence_potfolioapidevelopment.dao.entity.User;
import com.example.internintelligence_potfolioapidevelopment.dto.request.UserRequest;
import com.example.internintelligence_potfolioapidevelopment.dto.request.UserDetailsRequest;
import com.example.internintelligence_potfolioapidevelopment.dto.response.UserDetailsResponse;
import com.example.internintelligence_potfolioapidevelopment.dto.response.UserResponse;
import org.mapstruct.Mapper;
import org.mapstruct.MappingTarget;
import org.mapstruct.NullValuePropertyMappingStrategy;

@Mapper(componentModel = "spring",nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE)
public interface UserMapper {
    UserDetailsResponse toDetailsResponse(User user);
    User toEntity(UserRequest requestDto);
    UserResponse toResponse(User user);
    void mapForUpdate(@MappingTarget User user, UserDetailsRequest userDetailsRequest);
}
