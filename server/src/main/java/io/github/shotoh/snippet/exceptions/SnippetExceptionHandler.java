package io.github.shotoh.snippet.exceptions;

import io.github.shotoh.snippet.responses.Fail;
import java.util.HashMap;
import java.util.Map;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

@ControllerAdvice
public class SnippetExceptionHandler {
	@ExceptionHandler(value = ResourceAlreadyExistsException.class)
	public ResponseEntity<Fail> exception(ResourceAlreadyExistsException e) {
		ResponseEntity.BodyBuilder builder = ResponseEntity.status(HttpStatus.CONFLICT);
		Map<String, String> errorMap = e.getErrorMap();
		if (errorMap.isEmpty()) {
			return builder.body(new Fail());
		} else {
			return builder.body(new Fail(errorMap));
		}
	}

	@ExceptionHandler(value = ResourceNotFoundException.class)
	public ResponseEntity<Fail> exception(ResourceNotFoundException e) {
		ResponseEntity.BodyBuilder builder = ResponseEntity.status(HttpStatus.NOT_FOUND);
		Map<String, String> errorMap = e.getErrorMap();
		if (errorMap.isEmpty()) {
			return builder.body(new Fail());
		} else {
			return builder.body(new Fail(errorMap));
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

	@ExceptionHandler(value = BadCredentialsException.class)
	public ResponseEntity<Fail> exception(BadCredentialsException e) {
		Map<String, String> errors = new HashMap<>();
		errors.put("authentication", "Bad credentials");
		return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(new Fail(errors));
	}

	@ExceptionHandler(value = UnauthorizedException.class)
	public ResponseEntity<Fail> exception(UnauthorizedException e) {
		Map<String, String> errors = new HashMap<>();
		errors.put("authorization", "Unauthorized to perform this action");
		return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(new Fail(errors));
	}
}
