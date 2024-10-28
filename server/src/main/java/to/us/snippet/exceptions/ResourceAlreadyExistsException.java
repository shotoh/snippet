package to.us.snippet.exceptions;

import java.util.HashMap;
import java.util.Map;
import lombok.Getter;

@Getter
public class ResourceAlreadyExistsException extends RuntimeException {
	private final Map<String, String> errorMap;

	public ResourceAlreadyExistsException() {
		super();
		this.errorMap = new HashMap<>();
	}

	public ResourceAlreadyExistsException(String resource, String message) {
		this();
		this.errorMap.put(resource, message);
	}

	public ResourceAlreadyExistsException(Map<String, String> errorMap) {
		this();
		this.errorMap.putAll(errorMap);
	}
}
