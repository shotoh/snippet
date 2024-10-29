package to.us.snippet.responses;

import com.fasterxml.jackson.annotation.JsonValue;

public enum Status {
	SUCCESS("success"),
	FAIL("fail"),
	ERROR("error");

	private final String status;

	Status(String status) {
		this.status = status;
	}

	@JsonValue
	public String getStatus() {
		return status;
	}
}
