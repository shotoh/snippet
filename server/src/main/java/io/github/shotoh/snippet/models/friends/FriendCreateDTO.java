package io.github.shotoh.snippet.models.friends;

import jakarta.validation.constraints.Positive;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
public class FriendCreateDTO {
	private long fromId;

	@Positive
	private long toId;
}
