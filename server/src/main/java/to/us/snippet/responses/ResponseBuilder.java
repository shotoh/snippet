package to.us.snippet.responses;

public class ResponseBuilder {
	private final Status status;
	private Object data;
	private String message;

	public ResponseBuilder(Status status) {
		this.status = status;
	}

	public ResponseBuilder setData(Object data) {
		this.data = data;
		return this;
	}

	public ResponseBuilder setMessage(String message) {
		this.message = message;
		return this;
	}

	public Response build() {
		return switch (status) {
			case SUCCESS -> new Success(data);
			case FAIL -> new Fail(data);
			case ERROR -> new Error(message);
		};
	}
}
