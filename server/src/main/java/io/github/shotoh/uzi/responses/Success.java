package io.github.shotoh.uzi.responses;

import lombok.Getter;

@Getter
public class Success {
    private final String status = "success";
    private final Object data;

    public Success() {
        this.data = null;
    }

    public Success(Object data) {
        this.data = data;
    }
}
