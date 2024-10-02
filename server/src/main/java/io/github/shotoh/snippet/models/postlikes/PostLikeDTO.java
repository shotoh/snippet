package io.github.shotoh.snippet.models.postlikes;

import io.github.shotoh.snippet.models.posts.PostDTO;
import io.github.shotoh.snippet.models.users.UserDTO;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
public class PostLikeDTO {
	private long id;

	private UserDTO user;

	private PostDTO post;
}
