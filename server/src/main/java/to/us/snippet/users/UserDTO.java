package to.us.snippet.users;

import jakarta.validation.constraints.Size;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
public class UserDTO {
	private long id;

	private String username;

	private String email;

	@Size(min = 1, max = 31)
	private String displayName;

	@Size(min = 1, max = 1023)
	private String biography;

	private String profilePicture;
}
