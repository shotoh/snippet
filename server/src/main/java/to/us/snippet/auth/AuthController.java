package to.us.snippet.auth;

import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;
import to.us.snippet.responses.Response;
import to.us.snippet.responses.ResponseBuilder;
import to.us.snippet.responses.Status;
import to.us.snippet.users.UserCreateDTO;
import to.us.snippet.users.UserService;

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
	public Response register(@RequestBody @Valid UserCreateDTO userCreateDTO) {
		userService.createUser(userCreateDTO);
		return new ResponseBuilder(Status.SUCCESS).build();
	}

	@PostMapping("/login")
	public Response login(@RequestBody @Valid AuthDTO authDTO) {
		return new ResponseBuilder(Status.SUCCESS)
				.setData(service.login(authDTO))
				.build();
	}

	@PostMapping("/change-password")
	public Response changePassword(@RequestBody @Valid PasswordDTO passwordDTO) {
		service.changePassword(passwordDTO);
		return new ResponseBuilder(Status.SUCCESS).build();
	}
}
