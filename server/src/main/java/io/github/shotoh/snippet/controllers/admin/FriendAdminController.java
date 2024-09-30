package io.github.shotoh.snippet.controllers.admin;

import io.github.shotoh.snippet.models.friends.FriendDTO;
import io.github.shotoh.snippet.responses.Success;
import io.github.shotoh.snippet.services.FriendService;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping(path = "/api/admin/friends")
@PreAuthorize("hasRole('ADMIN')")
public class FriendAdminController {
	private final FriendService service;

	@Autowired
	public FriendAdminController(FriendService service) {
		this.service = service;
	}

	@GetMapping
	@ResponseStatus(HttpStatus.OK)
	public Success<List<FriendDTO>> retrieveFriends() {
		return new Success<>(service.retrieveFriends());
	}

	@GetMapping("/{id}")
	@ResponseStatus(HttpStatus.OK)
	public Success<FriendDTO> retrieveFriend(@PathVariable("id") long id) {
		return new Success<>(service.retrieveFriend(id));
	}
}