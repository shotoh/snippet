package to.us.snippet.friends;

import to.us.snippet.users.UserDTO;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
public class FriendDTO {
	private long id;

	private UserDTO from;

	private UserDTO to;

	private FriendStatus status;
}
