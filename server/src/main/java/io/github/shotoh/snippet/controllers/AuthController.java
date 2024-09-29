package io.github.shotoh.snippet.controllers;

import io.github.shotoh.snippet.models.auth.AuthDTO;
import io.github.shotoh.snippet.models.auth.TokenDTO;
import io.github.shotoh.snippet.models.users.UserCreateDTO;
import io.github.shotoh.snippet.responses.Success;
import io.github.shotoh.snippet.services.AuthService;
import io.github.shotoh.snippet.services.UserService;
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
