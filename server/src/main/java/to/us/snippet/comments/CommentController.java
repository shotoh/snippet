package to.us.snippet.comments;

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
import to.us.snippet.responses.Response;
import to.us.snippet.responses.ResponseBuilder;
import to.us.snippet.responses.Status;

@RestController
@RequestMapping(path = "/api/comments")
@PreAuthorize("hasRole('USER')")
public class CommentController {
	private final CommentService service;

	@Autowired
	public CommentController(CommentService service) {
		this.service = service;
	}

	@GetMapping
	@ResponseStatus(HttpStatus.OK)
	public Response retrieveComments(@RequestParam(name = "post") Optional<Long> postId) {
		ResponseBuilder builder = new ResponseBuilder(Status.SUCCESS);
		postId.map(post -> builder.setData(service.retrieveCommentsByPost(post)))
				.orElseGet(() -> builder.setData(service.retrieveComments()));
		return builder.build();
	}

	@PostMapping
	@ResponseStatus(HttpStatus.CREATED)
	public Response createComment(@RequestBody @Valid CommentCreateDTO commentCreateDTO) {
		return new ResponseBuilder(Status.SUCCESS)
				.setData(service.createComment(commentCreateDTO))
				.build();
	}

	@GetMapping("/{id}")
	@ResponseStatus(HttpStatus.OK)
	public Response retrieveComment(@PathVariable("id") long id) {
		return new ResponseBuilder(Status.SUCCESS)
				.setData(service.retrieveComment(id))
				.build();
	}

	@PatchMapping("/{id}")
	@ResponseStatus(HttpStatus.OK)
	public Response updateComment(@PathVariable("id") long id, @RequestBody @Valid CommentDTO commentDTO) {
		return new ResponseBuilder(Status.SUCCESS)
				.setData(service.updateComment(id, commentDTO))
				.build();
	}

	@DeleteMapping("/{id}")
	@ResponseStatus(HttpStatus.NO_CONTENT)
	public Response deleteComment(@PathVariable("id") long id) {
		service.deleteComment(id);
		return new ResponseBuilder(Status.SUCCESS).build();
	}
}
