package to.us.snippet.responses;

import java.util.Map;
import lombok.Getter;

@Getter
public class Fail {
	private final String status = "fail";
	private final Map<String, String> data;

	public Fail() {
		this.data = null;
	}

	public Fail(Map<String, String> data) {
		this.data = data;
	}
}
