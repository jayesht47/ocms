package com.jayesh.ocms.exceptions;


import jakarta.validation.ConstraintViolationException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import java.util.HashMap;
import java.util.Map;

@RestControllerAdvice
public class ControlAdvisor {

    @ExceptionHandler(exception = ConstraintViolationException.class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    public ResponseEntity<Map<String, Object>> constraintViolationExceptionHandler(ConstraintViolationException e) {
        Map<String, Object> map = new HashMap<>();
        map.put("error", true);
        map.put("message", "Mandatory fields missing");
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(map);
    }

}
