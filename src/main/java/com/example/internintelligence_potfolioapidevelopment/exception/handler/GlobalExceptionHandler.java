package com.example.internintelligence_potfolioapidevelopment.exception.handler;

import com.example.internintelligence_potfolioapidevelopment.exception.dto.ExceptionDto;
import com.example.internintelligence_potfolioapidevelopment.exception.AlreadyExistException;
import com.example.internintelligence_potfolioapidevelopment.exception.ForbiddenException;
import com.example.internintelligence_potfolioapidevelopment.exception.IllegalArgumentException;
import com.example.internintelligence_potfolioapidevelopment.exception.ResourceNotFoundException;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@RestControllerAdvice
public class GlobalExceptionHandler {
    @ResponseStatus(HttpStatus.CONFLICT)
    @ExceptionHandler(AlreadyExistException.class)
    public ExceptionDto handleAlreadyExistException(AlreadyExistException ex){
        return new ExceptionDto(ex.getMessage());
    }

    @ResponseStatus(HttpStatus.FORBIDDEN)
    @ExceptionHandler(ForbiddenException.class)
    public ExceptionDto handlerForbiddenException(ForbiddenException ex){
        return new ExceptionDto(ex.getMessage());
    }

    @ResponseStatus(HttpStatus.BAD_REQUEST)
    @ExceptionHandler(IllegalArgumentException.class)
    public ExceptionDto handlerIllegalArgumentException(IllegalArgumentException ex){
        return new ExceptionDto(ex.getMessage());
    }

    @ResponseStatus(HttpStatus.NOT_FOUND)
    @ExceptionHandler(ResourceNotFoundException.class)
    public ExceptionDto handleResourceNotFoundException(ResourceNotFoundException ex){
        return new ExceptionDto(ex.getMessage());
    }

}
