package io.github.shotoh.snippet.models.medias;

import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;
import jakarta.validation.constraints.Size;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
public class MediaCreateDTO {
	private long id;

	@Positive
	private long postId;

	@NotNull
	@Size(min = 1, max = 1023)
	private String source;
}
