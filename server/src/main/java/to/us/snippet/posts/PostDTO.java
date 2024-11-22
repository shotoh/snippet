package to.us.snippet.posts;

import jakarta.validation.constraints.Size;
import java.util.List;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import to.us.snippet.users.UserDTO;

@Getter
@Setter
@NoArgsConstructor
public class PostDTO {
	private long id;

	private UserDTO user;

	@Size(min = 1, max = 1023)
	private String content;

	private int liked;

	private int totalLikes;

	private int totalDislikes;

	private List<String> images;

	private long timestamp;
}
