package com.jayesh.ocms.exceptions;


import jakarta.validation.ConstraintViolationException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import java.util.HashMap;
import java.util.Map;

@RestControllerAdvice
public class ControlAdvisor {

    private static final String ERROR = "error";
    private static final String ERROR_MESSAGE = "errorMessage";

    @ExceptionHandler(exception = ConstraintViolationException.class)
    public ResponseEntity<Map<String, Object>> constraintViolationExceptionHandler(ConstraintViolationException e) {
        Map<String, Object> map = new HashMap<>();
        map.put(ERROR, true);
        map.put(ERROR_MESSAGE, "Mandatory fields missing");
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(map);
    }

    @ExceptionHandler(exception = NotFoundException.class)
    public ResponseEntity<Map<String, Object>> notFoundExceptionHandler(NotFoundException e) {
        Map<String, Object> map = new HashMap<>();
        map.put(ERROR, true);
        map.put(ERROR_MESSAGE, e.getMessage());
        return ResponseEntity.status(HttpStatus.NOT_FOUND).body(map);
    }


    @ExceptionHandler(exception = BadCredentialsException.class)
    public ResponseEntity<Map<String, Object>> badCredentialsExceptionHandler(BadCredentialsException e) {
        Map<String, Object> map = new HashMap<>();
        map.put(ERROR, true);
        map.put(ERROR_MESSAGE, e.getMessage());
        return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(map);
    }


}
