package to.us.snippet.images;

import jakarta.validation.constraints.NotNull;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
public class ImageCreateDTO {
	private long postId;

	@NotNull
	private String content;
}
