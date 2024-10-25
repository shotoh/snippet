package to.us.snippet.responses;

public enum Status {
	SUCCESS("success"),
	FAIL("fail"),
	ERROR("error");

	private final String status;

	Status(String status) {
		this.status = status;
	}

	@Override
	public String toString() {
		return status;
	}
}
