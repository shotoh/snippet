package to.us.snippet.security;

import to.us.snippet.security.AuthDTO;
import to.us.snippet.security.TokenDTO;
import to.us.snippet.users.UserCreateDTO;
import to.us.snippet.responses.Success;
import to.us.snippet.security.AuthService;
import to.us.snippet.users.UserService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping(path = "/api/auth")
public class AuthController {
	private final AuthService service;
	private final UserService userService;

	@Autowired
	public AuthController(AuthService service, UserService userService) {
		this.service = service;
		this.userService = userService;
	}

	@PostMapping("/register")
	@ResponseStatus(HttpStatus.CREATED)
	public Success<Void> register(@RequestBody @Valid UserCreateDTO userCreateDTO) {
		userService.createUser(userCreateDTO);
		return new Success<>();
	}

	@PostMapping("/login")
	public Success<TokenDTO> login(@RequestBody @Valid AuthDTO authDTO) {
		return new Success<>(service.login(authDTO));
	}
}
