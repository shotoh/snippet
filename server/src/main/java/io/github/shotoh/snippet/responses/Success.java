package io.github.shotoh.snippet.responses;

import lombok.Getter;

@Getter
public class Success<T> {
	private final String status = "success";
	private final T data;

	public Success() {
		this.data = null;
	}

	public Success(T data) {
		this.data = data;
	}
}
