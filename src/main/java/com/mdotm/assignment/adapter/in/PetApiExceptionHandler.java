package com.mdotm.assignment.adapter.in;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import com.mdotm.assignment.domain.exception.PetNotFoundException;

@RestControllerAdvice
public class PetApiExceptionHandler {

    @ExceptionHandler(PetNotFoundException.class)
    public ResponseEntity<?> handleNotFound(PetNotFoundException exception) {
        return ResponseEntity.notFound().build();
    }
}