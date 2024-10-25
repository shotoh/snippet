package to.us.snippet.commentlikes;

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
@RequestMapping(path = "/api/comment-likes")
@PreAuthorize("hasRole('USER')")
public class CommentLikeController {
	private final CommentLikeService service;

	@Autowired
	public CommentLikeController(CommentLikeService service) {
		this.service = service;
	}

	@GetMapping
	@ResponseStatus(HttpStatus.OK)
	public Response retrieveCommentLikes(@RequestParam(name = "comment") long commentId, @RequestParam(name = "user") Optional<Long> userId) {
		ResponseBuilder builder = new ResponseBuilder(Status.SUCCESS);
		userId.map(user -> builder.setData(List.of(service.retrieveCommentLikeByUserAndComment(user, commentId))))
				.orElseGet(() -> builder.setData(service.retrieveCommentLikesByComment(commentId)));
		return builder.build();
	}

	@PostMapping
	@ResponseStatus(HttpStatus.CREATED)
	public Response createCommentLike(@RequestBody @Valid CommentLikeCreateDTO commentLikeCreateDTO) {
		return new ResponseBuilder(Status.SUCCESS)
				.setData(service.createCommentLike(commentLikeCreateDTO))
				.build();
	}

	@GetMapping("/{id}")
	@ResponseStatus(HttpStatus.OK)
	public Response retrieveCommentLike(@PathVariable("id") long id) {
		return new ResponseBuilder(Status.SUCCESS)
				.setData(service.retrieveCommentLike(id))
				.build();
	}

	@DeleteMapping("/{id}")
	@ResponseStatus(HttpStatus.NO_CONTENT)
	public Response deleteCommentLike(@PathVariable("id") long id) {
		service.deleteCommentLike(id);
		return new ResponseBuilder(Status.SUCCESS).build();
	}
}
