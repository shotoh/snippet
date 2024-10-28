package to.us.snippet.posts;

import to.us.snippet.users.UserDTO;
import jakarta.validation.constraints.Size;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
public class PostDTO {
	private long id;

	private UserDTO user;

	@Size(min = 1, max = 1023)
	private String content;
}
