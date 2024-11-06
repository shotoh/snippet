package to.us.snippet.posts;

import jakarta.validation.Valid;
import java.util.Optional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;
import to.us.snippet.responses.Response;
import to.us.snippet.responses.ResponseBuilder;
import to.us.snippet.responses.Status;

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
	public Response retrievePosts(@RequestParam(name = "user") Optional<Long> userId) {
		ResponseBuilder builder = new ResponseBuilder(Status.SUCCESS);
		userId.map(user -> builder.setData(service.retrievePostsByUser(user)))
				.orElseGet(() -> builder.setData(service.retrievePosts()));
		return builder.build();
	}

	@PostMapping
	@ResponseStatus(HttpStatus.CREATED)
	public Response createPost(@RequestBody @Valid PostCreateDTO postCreateDTO) {
		return new ResponseBuilder(Status.SUCCESS)
				.setData(service.createPost(postCreateDTO))
				.build();
	}

	@GetMapping("/{id}")
	@ResponseStatus(HttpStatus.OK)
	public Response retrievePost(@PathVariable("id") long id) {
		return new ResponseBuilder(Status.SUCCESS)
				.setData(service.retrievePost(id))
				.build();
	}

	@PatchMapping("/{id}")
	@ResponseStatus(HttpStatus.OK)
	public Response updatePost(@PathVariable("id") long id, @RequestBody @Valid PostDTO postDTO) {
		return new ResponseBuilder(Status.SUCCESS)
				.setData(service.updatePost(id, postDTO))
				.build();
	}

	@PostMapping("/{id}/picture")
	@ResponseStatus(HttpStatus.NO_CONTENT)
	public Response addPostPicture(@PathVariable("id") long id, @RequestParam("file") MultipartFile file) {
		service.addPostPicture(id, file);
		return new ResponseBuilder(Status.SUCCESS).build();
	}

	@PatchMapping("/{id}/like")
	@ResponseStatus(HttpStatus.NO_CONTENT)
	public Response likePost(@PathVariable("id") long id) {
		service.likePost(id);
		return new ResponseBuilder(Status.SUCCESS).build();
	}

	@PatchMapping("/{id}/dislike")
	@ResponseStatus(HttpStatus.NO_CONTENT)
	public Response dislikePost(@PathVariable("id") long id) {
		service.dislikePost(id);
		return new ResponseBuilder(Status.SUCCESS).build();
	}

	@DeleteMapping("/{id}/like")
	@ResponseStatus(HttpStatus.NO_CONTENT)
	public Response unlikePost(@PathVariable("id") long id) {
		service.unlikePost(id);
		return new ResponseBuilder(Status.SUCCESS).build();
	}

	@DeleteMapping("/{id}")
	@ResponseStatus(HttpStatus.NO_CONTENT)
	public Response deletePost(@PathVariable("id") long id) {
		service.deletePost(id);
		return new ResponseBuilder(Status.SUCCESS).build();
	}
}
