package com.example.internintelligence_potfolioapidevelopment.mapper;

import com.example.internintelligence_potfolioapidevelopment.dao.entity.User;
import com.example.internintelligence_potfolioapidevelopment.dto.UserDto;
import com.example.internintelligence_potfolioapidevelopment.dto.request.RegisterRequest;
import com.example.internintelligence_potfolioapidevelopment.dto.request.UserDetailsRequestDto;
import com.example.internintelligence_potfolioapidevelopment.dto.response.UserResponseDto;
import org.mapstruct.Mapper;
import org.mapstruct.MappingTarget;
import org.mapstruct.NullValuePropertyMappingStrategy;

@Mapper(componentModel = "spring",nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE)
public interface UserMapper {
    User toEntity(UserDto dto);
    UserDto toDto(User user);
    User toEntity(RegisterRequest requestDto);
    UserResponseDto toResponse(User user);

    void mapForUpdate(@MappingTarget User user, UserDetailsRequestDto userDetailsRequestDto);
}
