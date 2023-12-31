package com.example.beerservice.web.exception.handler;

import com.example.beerservice.web.exception.NotFoundException;
import jakarta.validation.ConstraintViolation;
import jakarta.validation.ConstraintViolationException;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindException;
import org.springframework.validation.ObjectError;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

import java.util.List;
import java.util.stream.Collectors;

@ControllerAdvice
public class MvcExceptionHandler {
    @ExceptionHandler(ConstraintViolationException.class)
    public ResponseEntity<List<String>> handleValidationError(ConstraintViolationException e) {
        final List<String> errors = e.getConstraintViolations().stream()
                .map(ConstraintViolation::getMessage)
                .collect(Collectors.toList());
        return ResponseEntity.badRequest().body(errors);
    }
    @ExceptionHandler(BindException.class)
    public ResponseEntity<List<ObjectError>> handleValidationError(BindException e) {
        return ResponseEntity.badRequest().body(e.getAllErrors());
    }
    @ExceptionHandler(NotFoundException.class)
    public ResponseEntity<List<ObjectError>> handleNotFoundException(NotFoundException e) {
        return ResponseEntity.notFound().build();
    }
}
