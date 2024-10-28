package to.us.snippet.commentlikes;

import to.us.snippet.comments.CommentDTO;
import to.us.snippet.users.UserDTO;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
public class CommentLikeDTO {
	private long id;

	private UserDTO user;

	private CommentDTO comment;
}
