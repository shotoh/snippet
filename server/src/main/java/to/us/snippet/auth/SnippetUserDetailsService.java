package to.us.snippet.auth;

import to.us.snippet.users.User;
import to.us.snippet.users.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.stereotype.Service;

@Service
public class SnippetUserDetailsService implements UserDetailsService {
	private final UserRepository repository;

	@Autowired
	public SnippetUserDetailsService(UserRepository repository) {
		this.repository = repository;
	}

	@Override
	public UserDetails loadUserByUsername(String username) {
		User user = repository.findByUsername(username);
		if (user == null) {
			throw new BadCredentialsException("Bad credentials");
		}
		return org.springframework.security.core.userdetails.User.builder()
				.username(username)
				.password(user.getEncryptedPassword())
				.roles(user.getRole())
				.build();
	}
}
