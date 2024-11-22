package to.us.snippet.users;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
public class UserCreateDTO {
	@NotNull
	@Size(min = 1, max = 31)
	private String username;

	@NotNull
	@Email
	private String email;

	@NotNull
	private String password;
}
