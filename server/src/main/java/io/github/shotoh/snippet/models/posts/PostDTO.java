package io.github.shotoh.snippet.models.posts;

import io.github.shotoh.snippet.models.users.UserDTO;
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

	@Size(min = 1, max = 63)
	private String title;

	@Size(min = 1, max = 1023)
	private String content;
}
