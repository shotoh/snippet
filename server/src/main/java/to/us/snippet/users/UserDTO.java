package to.us.snippet.users;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.Size;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
public class UserDTO {
	private long id;

	@Size(min = 1, max = 31)
	private String username;

	@Email
	private String email;

	@Size(min = 1, max = 31)
	private String displayName;

	private String biography;
}
