package com.example.internintelligence_potfolioapidevelopment.exception.handler;

import com.example.internintelligence_potfolioapidevelopment.dto.response.ExceptionDto;
import com.example.internintelligence_potfolioapidevelopment.exception.AlreadyExistException;
import com.example.internintelligence_potfolioapidevelopment.exception.ForbiddenException;
import com.example.internintelligence_potfolioapidevelopment.exception.IllegalArgumentException;
import com.example.internintelligence_potfolioapidevelopment.exception.ResourceNotFoundException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import java.util.HashMap;
import java.util.Map;

@RestControllerAdvice
public class GlobalExceptionHandler {

    //validation
//    @ExceptionHandler(MethodArgumentNotValidException.class)
//    public ResponseEntity<Map<String, String>> handler(MethodArgumentNotValidException ex) {
//        Map<String, String> errors = new HashMap<>();
//        ex.getBindingResult().getAllErrors().forEach((error) -> {
//            String fieldName = ((FieldError) error).getField();
//            String errorMessage = error.getDefaultMessage();
//            errors.put(fieldName, errorMessage);
//        });
//        return new ResponseEntity<>(errors, HttpStatus.BAD_REQUEST);
//    }
    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<Map<String, String>> handler(MethodArgumentNotValidException ex) {
        Map<String, String> errors = new HashMap<>();
        ex.getBindingResult().getAllErrors().forEach((error) -> {
            if (error instanceof FieldError) { // Check if the error is a FieldError
                String fieldName = ((FieldError) error).getField();
                String errorMessage = error.getDefaultMessage();
                errors.put(fieldName, errorMessage);
            } else {
                // Handle non-FieldError cases here if necessary
                errors.put("error", "Invalid input");
            }
        });
        return new ResponseEntity<>(errors, HttpStatus.BAD_REQUEST);
    }

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
