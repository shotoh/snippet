package io.github.shotoh.snippet.controllers;

import io.github.shotoh.snippet.models.users.UserDTO;
import io.github.shotoh.snippet.responses.Success;
import io.github.shotoh.snippet.services.UserService;
import jakarta.validation.Valid;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping(path = "/api/users")
@PreAuthorize("hasRole('USER')")
public class UserController {
	private final UserService service;

	@Autowired
	public UserController(UserService service) {
		this.service = service;
	}

	@GetMapping
	@ResponseStatus(HttpStatus.OK)
	public Success<List<UserDTO>> retrieveUsers() {
		return new Success<>(service.retrieveUsers());
	}

	@GetMapping("/{id}")
	@ResponseStatus(HttpStatus.OK)
	public Success<UserDTO> retrieveUser(@PathVariable("id") long id) {
		return new Success<>(service.retrieveUser(id));
	}

	@PatchMapping("/{id}")
	@ResponseStatus(HttpStatus.OK)
	public Success<UserDTO> updateUser(@PathVariable("id") long id, @RequestBody @Valid UserDTO userDTO) {
		return new Success<>(service.updateUser(id, userDTO));
	}

	@DeleteMapping("/{id}")
	@ResponseStatus(HttpStatus.NO_CONTENT)
	public Success<Void> deleteUser(@PathVariable("id") long id) {
		service.deleteUser(id);
		return new Success<>();
	}
}
