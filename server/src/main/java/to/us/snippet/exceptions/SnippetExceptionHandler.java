package to.us.snippet.exceptions;

import java.util.HashMap;
import java.util.Map;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import to.us.snippet.responses.Response;
import to.us.snippet.responses.ResponseBuilder;
import to.us.snippet.responses.Status;

@ControllerAdvice
public class SnippetExceptionHandler {
	@ExceptionHandler(value = ResourceAlreadyExistsException.class)
	public ResponseEntity<Response> exception(ResourceAlreadyExistsException e) {
		ResponseEntity.BodyBuilder builder = ResponseEntity.status(HttpStatus.CONFLICT);
		Map<String, String> errorMap = e.getErrorMap();
		if (errorMap.isEmpty()) {
			return builder.body(new ResponseBuilder(Status.FAIL).build());
		} else {
			return builder.body(new ResponseBuilder(Status.FAIL).setData(errorMap).build());
		}
	}

	@ExceptionHandler(value = ResourceNotFoundException.class)
	public ResponseEntity<Response> exception(ResourceNotFoundException e) {
		ResponseEntity.BodyBuilder builder = ResponseEntity.status(HttpStatus.NOT_FOUND);
		Map<String, String> errorMap = e.getErrorMap();
		if (errorMap.isEmpty()) {
			return builder.body(new ResponseBuilder(Status.FAIL).build());
		} else {
			return builder.body(new ResponseBuilder(Status.FAIL).setData(errorMap).build());
		}
	}

	@ExceptionHandler(value = MethodArgumentNotValidException.class)
	public ResponseEntity<Response> exception(MethodArgumentNotValidException e) {
		Map<String, String> errors = new HashMap<>();
		e.getBindingResult().getAllErrors().forEach((error) -> {
			if (error instanceof FieldError fieldError) {
				errors.put(fieldError.getField(), fieldError.getDefaultMessage());
			}
		});
		return ResponseEntity.badRequest().body(new ResponseBuilder(Status.FAIL).setData(errors).build());
	}

	@ExceptionHandler(value = BadCredentialsException.class)
	public ResponseEntity<Response> exception(BadCredentialsException e) {
		Map<String, String> errors = new HashMap<>();
		errors.put("authentication", "Bad credentials");
		return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(new ResponseBuilder(Status.FAIL).setData(errors).build());
	}

	@ExceptionHandler(value = UnauthorizedException.class)
	public ResponseEntity<Response> exception(UnauthorizedException e) {
		Map<String, String> errors = new HashMap<>();
		errors.put("authorization", "Unauthorized to perform this action");
		return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(new ResponseBuilder(Status.FAIL).setData(errors).build());
	}
}
