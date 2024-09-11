package io.github.shotoh.uzi.exceptions;

import lombok.Getter;

@Getter
public class ResourceAlreadyExistsException extends RuntimeException {
    private final String resource;

    public ResourceAlreadyExistsException() {
        super();
        this.resource = null;
    }

    public ResourceAlreadyExistsException(String resource, String message) {
        super(message);
        this.resource = resource;
    }
}
