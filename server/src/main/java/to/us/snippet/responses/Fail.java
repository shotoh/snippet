package to.us.snippet.responses;

import lombok.Getter;

@Getter
public class Fail extends Response {
	private final Object data;

	public Fail(Object data) {
		super(Status.FAIL);
		this.data = data;
	}
}
