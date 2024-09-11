package io.github.shotoh.uzi.exceptions;

import lombok.Getter;

@Getter
public class ResourceNotFoundException extends RuntimeException {
    private final String resource;

    public ResourceNotFoundException() {
        super();
        this.resource = null;
    }

    public ResourceNotFoundException(String resource, String message) {
        super(message);
        this.resource = resource;
    }
}
