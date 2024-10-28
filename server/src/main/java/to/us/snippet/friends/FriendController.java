package to.us.snippet.friends;

import jakarta.validation.Valid;
import java.util.List;
import java.util.Optional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;
import to.us.snippet.exceptions.UnauthorizedException;
import to.us.snippet.responses.Response;
import to.us.snippet.responses.ResponseBuilder;
import to.us.snippet.responses.Status;

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
	public Response retrieveFriends(@RequestParam(name = "from") Optional<Long> fromId, @RequestParam(name = "to") Optional<Long> toId) {
		ResponseBuilder builder = new ResponseBuilder(Status.SUCCESS);
		if (fromId.isPresent() && toId.isPresent()) {
			return builder.setData(List.of(service.retrieveFriendByFromAndTo(fromId.get(), toId.get()))).build();
		} else if (fromId.isPresent()) {
			return builder.setData(service.retrieveFriendsByFrom(fromId.get())).build();
		} else if (toId.isPresent()) {
			return builder.setData(service.retrieveFriendsByTo(toId.get())).build();
		} else {
			throw new UnauthorizedException();
		}
	}

	@PostMapping
	@ResponseStatus(HttpStatus.CREATED)
	public Response createFriend(@RequestBody @Valid FriendCreateDTO friendCreateDTO) {
		return new ResponseBuilder(Status.SUCCESS)
				.setData(service.createFriend(friendCreateDTO))
				.build();
	}

	@DeleteMapping("/{id}")
	@ResponseStatus(HttpStatus.NO_CONTENT)
	public Response deleteFriend(@PathVariable("id") long id) {
		service.deleteFriend(id);
		return new ResponseBuilder(Status.SUCCESS).build();
	}
}
