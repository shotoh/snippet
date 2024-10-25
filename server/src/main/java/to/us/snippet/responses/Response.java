package to.us.snippet.responses;

import lombok.Getter;

@Getter
public class Response {
	private final Status status;

	public Response(Status status) {
		this.status = status;
	}
}
