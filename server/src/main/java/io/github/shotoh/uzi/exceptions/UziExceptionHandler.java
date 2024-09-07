package io.github.shotoh.uzi.exceptions;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

@ControllerAdvice
public class UziExceptionHandler {
    @ExceptionHandler(value = ResourceAlreadyExistsException.class)
    public ResponseEntity<String> exception(ResourceAlreadyExistsException e) {
        return ResponseEntity.status(HttpStatus.CONFLICT).body(e.getMessage());
    }

    @ExceptionHandler(value = ResourceNotFoundException.class)
    public ResponseEntity<String> exception(ResourceNotFoundException e) {
        return ResponseEntity.status(HttpStatus.NOT_FOUND).body(e.getMessage());
    }

    @ExceptionHandler(value = ValidationException.class)
    public ResponseEntity<String> exception(ValidationException e) {
        return ResponseEntity.badRequest().body(e.getMessage());
    }
}
