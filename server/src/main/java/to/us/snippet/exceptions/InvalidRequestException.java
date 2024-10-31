package to.us.snippet.exceptions;

import java.util.HashMap;
import java.util.Map;
import lombok.Getter;

@Getter
public class InvalidRequestException extends RuntimeException {
	private final Map<String, String> errorMap;

	public InvalidRequestException() {
		super();
		this.errorMap = new HashMap<>();
	}

	public InvalidRequestException(String resource, String message) {
		this();
		this.errorMap.put(resource, message);
	}

	public InvalidRequestException(Map<String, String> errorMap) {
		this();
		this.errorMap.putAll(errorMap);
	}
}
