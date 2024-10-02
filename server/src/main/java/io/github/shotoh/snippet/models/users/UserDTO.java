package io.github.shotoh.snippet.models.users;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.Size;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
public class UserDTO {
	private static final int USERNAME_MIN_LEN = 3;
	private static final int USERNAME_MAX_LEN = 31;

	private long id;

	@Size(min = USERNAME_MIN_LEN, max = USERNAME_MAX_LEN)
	private String username;

	@Email
	private String email;

	@Size(min = USERNAME_MIN_LEN, max = USERNAME_MAX_LEN)
	private String displayName;

	private String profilePicture;

	private String biography;
}
