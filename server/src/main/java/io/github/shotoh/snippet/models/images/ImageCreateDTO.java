package io.github.shotoh.snippet.models.images;

import jakarta.validation.constraints.NotNull;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
public class ImageCreateDTO {
	private long userId;

	private long postId;

	@NotNull
	private String content;
}
