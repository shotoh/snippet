package io.github.shotoh.snippet.exceptions;

import java.util.HashMap;
import java.util.Map;
import lombok.Getter;

@Getter
public class ResourceNotFoundException extends RuntimeException {
	private final Map<String, String> errorMap;

	public ResourceNotFoundException() {
		super();
		this.errorMap = new HashMap<>();
	}

	public ResourceNotFoundException(String resource, String message) {
		this();
		this.errorMap.put(resource, message);
	}

	public ResourceNotFoundException(Map<String, String> errorMap) {
		this();
		this.errorMap.putAll(errorMap);
	}
}
