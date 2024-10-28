package to.us.snippet.responses;

import lombok.Getter;

@Getter
public class Error extends Response {
	private final String message;

	public Error(String message) {
		super(Status.ERROR);
		this.message = message;
	}
}
