package to.us.snippet.auth;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
public class PasswordDTO {
	private String oldPassword;
	private String newPassword;
}
