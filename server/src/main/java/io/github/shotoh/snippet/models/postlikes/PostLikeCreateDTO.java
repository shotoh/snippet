package io.github.shotoh.snippet.models.postlikes;

import jakarta.validation.constraints.Positive;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
public class PostLikeCreateDTO {
	private long id;

	@Positive
	private long userId;

	@Positive
	private long postId;
}
