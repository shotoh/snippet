package io.github.shotoh.uzi.exceptions;

import io.github.shotoh.uzi.responses.Fail;
import java.util.HashMap;
import java.util.Map;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

@ControllerAdvice
public class UziExceptionHandler {
    @ExceptionHandler(value = ResourceAlreadyExistsException.class)
    public ResponseEntity<Fail> exception(ResourceAlreadyExistsException e) {
        ResponseEntity.BodyBuilder builder = ResponseEntity.status(HttpStatus.CONFLICT);
        String resource = e.getResource();
        if (resource == null) {
            return builder.body(new Fail());
        } else {
            return builder.body(new Fail(Map.of(resource, e.getMessage())));
        }
    }

    @ExceptionHandler(value = ResourceNotFoundException.class)
    public ResponseEntity<Fail> exception(ResourceNotFoundException e) {
        ResponseEntity.BodyBuilder builder = ResponseEntity.status(HttpStatus.NOT_FOUND);
        String resource = e.getResource();
        if (resource == null) {
            return builder.body(new Fail());
        } else {
            return builder.body(new Fail(Map.of(resource, e.getMessage())));
        }
    }

    @ExceptionHandler(value = MethodArgumentNotValidException.class)
    public ResponseEntity<Fail> exception(MethodArgumentNotValidException e) {
        Map<String, String> errors = new HashMap<>();
        e.getBindingResult().getAllErrors().forEach((error) -> {
            if (error instanceof FieldError fieldError) {
                errors.put(fieldError.getField(), fieldError.getDefaultMessage());
            }
        });
        return ResponseEntity.badRequest().body(new Fail(errors));
    }
}
