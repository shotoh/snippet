package to.us.snippet.responses;

import lombok.Getter;

@Getter
public class Success extends Response {
	private final Object data;

	public Success(Object data) {
		super(Status.SUCCESS);
		this.data = data;
	}
}
