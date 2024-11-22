package to.us.snippet.comments;

import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;
import jakarta.validation.constraints.Size;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
public class CommentCreateDTO {
	private long userId;

	@Positive
	private long postId;

	@NotNull
	@Size(min = 1, max = 1023)
	private String content;
}
