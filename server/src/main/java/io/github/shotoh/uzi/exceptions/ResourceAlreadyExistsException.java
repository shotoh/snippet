package io.github.shotoh.uzi.exceptions;

import java.util.Map;
import lombok.Getter;

@Getter
public class ResourceAlreadyExistsException extends RuntimeException {
    private final Map<String, String> errorMap;

    public ResourceAlreadyExistsException() {
        super();
        this.errorMap = null;
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
