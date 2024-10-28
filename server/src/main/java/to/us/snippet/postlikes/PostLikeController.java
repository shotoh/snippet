package to.us.snippet.postlikes;

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
import to.us.snippet.responses.Response;
import to.us.snippet.responses.ResponseBuilder;
import to.us.snippet.responses.Status;

@RestController
@RequestMapping(path = "/api/post-likes")
@PreAuthorize("hasRole('USER')")
public class PostLikeController {
	private final PostLikeService service;

	@Autowired
	public PostLikeController(PostLikeService service) {
		this.service = service;
	}

	@GetMapping
	@ResponseStatus(HttpStatus.OK)
	public Response retrievePostLikes(@RequestParam(name = "post") long postId, @RequestParam(name = "user") Optional<Long> userId) {
		ResponseBuilder builder = new ResponseBuilder(Status.SUCCESS);
		userId.map(user -> builder.setData(List.of(service.retrievePostLikeByUserAndPost(user, postId))))
				.orElseGet(() -> builder.setData(service.retrievePostLikesByPost(postId)));
		return builder.build();
	}

	@PostMapping
	@ResponseStatus(HttpStatus.CREATED)
	public Response createPostLike(@RequestBody @Valid PostLikeCreateDTO postLikeCreateDTO) {
		return new ResponseBuilder(Status.SUCCESS)
				.setData(service.createPostLike(postLikeCreateDTO))
				.build();
	}

	@GetMapping("/{id}")
	@ResponseStatus(HttpStatus.OK)
	public Response retrievePostLike(@PathVariable("id") long id) {
		return new ResponseBuilder(Status.SUCCESS)
				.setData(service.retrievePostLike(id))
				.build();
	}

	@DeleteMapping("/{id}")
	@ResponseStatus(HttpStatus.NO_CONTENT)
	public Response deletePostLike(@PathVariable("id") long id) {
		service.deletePostLike(id);
		return new ResponseBuilder(Status.SUCCESS).build();
	}
}