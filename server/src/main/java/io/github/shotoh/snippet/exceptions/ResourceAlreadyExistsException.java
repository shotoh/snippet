package io.github.shotoh.snippet.exceptions;

import lombok.Getter;

import java.util.HashMap;
import java.util.Map;

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
