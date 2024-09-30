package io.github.shotoh.snippet.responses;

import lombok.Getter;

@Getter
public class Error {
	private final String status = "error";
	private final String message;

	public Error() {
		this.message = null;
	}

	public Error(String message) {
		this.message = message;
	}
}
