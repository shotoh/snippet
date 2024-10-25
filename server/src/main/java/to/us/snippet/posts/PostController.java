package to.us.snippet.posts;

import to.us.snippet.responses.Success;
import jakarta.validation.Valid;
import java.util.List;
import java.util.Optional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping(path = "/api/posts")
@PreAuthorize("hasRole('USER')")
public class PostController {
	private final PostService service;

	@Autowired
	public PostController(PostService service) {
		this.service = service;
	}

	@GetMapping
	@ResponseStatus(HttpStatus.OK)
	public Success<List<PostDTO>> retrievePosts(@RequestParam(name = "user") Optional<Long> userId) {
		return userId
                .map(user -> new Success<>(service.retrievePostsByUser(user)))
                .orElseGet(() -> new Success<>(service.retrievePosts()));
	}

	@PostMapping
	@ResponseStatus(HttpStatus.CREATED)
	public Success<PostDTO> createPost(@RequestBody @Valid PostCreateDTO postCreateDTO) {
		return new Success<>(service.createPost(postCreateDTO));
	}

	@PatchMapping("/{id}")
	@ResponseStatus(HttpStatus.OK)
	public Success<PostDTO> updatePost(@PathVariable("id") long id, @RequestBody @Valid PostDTO postDTO) {
		return new Success<>(service.updatePost(id, postDTO));
	}

	@DeleteMapping("/{id}")
	@ResponseStatus(HttpStatus.NO_CONTENT)
	public Success<Void> deletePost(@PathVariable("id") long id) {
		service.deletePost(id);
		return new Success<>();
	}
}
