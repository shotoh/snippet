package to.us.snippet.postlikes;

import to.us.snippet.posts.PostDTO;
import to.us.snippet.users.UserDTO;
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
