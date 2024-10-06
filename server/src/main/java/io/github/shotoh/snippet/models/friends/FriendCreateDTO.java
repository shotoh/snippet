package io.github.shotoh.snippet.models.friends;

import io.github.shotoh.snippet.models.users.UserDTO;
import jakarta.validation.constraints.Positive;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
public class FriendCreateDTO {
	private UserDTO from;

	@Positive
	private UserDTO to;
}
