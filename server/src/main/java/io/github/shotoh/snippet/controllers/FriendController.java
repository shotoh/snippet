package io.github.shotoh.snippet.controllers;

import io.github.shotoh.snippet.models.friends.FriendCreateDTO;
import io.github.shotoh.snippet.models.friends.FriendDTO;
import io.github.shotoh.snippet.responses.Success;
import io.github.shotoh.snippet.services.FriendService;
import jakarta.validation.Valid;
import java.util.List;
import java.util.Optional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping(path = "/api/friends")
@PreAuthorize("hasRole('USER')")
public class FriendController {
	private final FriendService service;

	@Autowired
	public FriendController(FriendService service) {
		this.service = service;
	}

	@GetMapping
	@ResponseStatus(HttpStatus.OK)
	public Success<List<FriendDTO>> retrieveFriendsByFrom(@RequestParam(name = "from") long fromId, @RequestParam(name = "to") Optional<Long> toId) {
		return toId
				.map(to -> new Success<>(List.of(service.retrieveFriendByFromAndTo(fromId, to))))
				.orElseGet(() -> new Success<>(service.retrieveFriendsByFrom(fromId)));
	}

	@PostMapping
	@ResponseStatus(HttpStatus.CREATED)
	public Success<FriendDTO> createFriend(@RequestBody @Valid FriendCreateDTO friendCreateDTO) {
		return new Success<>(service.createFriend(friendCreateDTO));
	}

	@DeleteMapping("/{id}")
	@ResponseStatus(HttpStatus.NO_CONTENT)
	public Success<Void> deleteFriend(@PathVariable("id") long id) {
		service.deleteFriend(id);
		return new Success<>();
	}
}
