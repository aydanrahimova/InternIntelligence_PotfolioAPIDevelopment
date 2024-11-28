package com.example.internintelligence_potfolioapidevelopment.mapper;

public interface Mapper<T, R> {
    R toDto(T entity);
}