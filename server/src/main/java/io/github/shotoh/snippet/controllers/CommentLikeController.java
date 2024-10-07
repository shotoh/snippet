package io.github.shotoh.snippet.controllers;

import io.github.shotoh.snippet.models.commentlikes.CommentLikeCreateDTO;
import io.github.shotoh.snippet.models.commentlikes.CommentLikeDTO;
import io.github.shotoh.snippet.responses.Success;
import io.github.shotoh.snippet.services.CommentLikeService;
import jakarta.validation.Valid;
import java.util.List;
import java.util.Optional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

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
	public Success<List<CommentLikeDTO>> retrieveCommentLikes(@RequestParam(name = "comment") long commentId, @RequestParam(name = "user") Optional<Long> userId) {
		return userId
				.map(user -> new Success<>(List.of(service.retrieveCommentLikeByUserAndComment(user, commentId))))
				.orElseGet(() -> new Success<>(service.retrieveCommentLikesByComment(commentId)));
	}

	@PostMapping
	@ResponseStatus(HttpStatus.CREATED)
	public Success<CommentLikeDTO> createCommentLike(@RequestBody @Valid CommentLikeCreateDTO commentLikeCreateDTO) {
		return new Success<>(service.createCommentLike(commentLikeCreateDTO));
	}

	@GetMapping("/{id}")
	@ResponseStatus(HttpStatus.OK)
	public Success<CommentLikeDTO> retrieveCommentLike(@PathVariable("id") long id) {
		return new Success<>(service.retrieveCommentLike(id));
	}

	@DeleteMapping("/{id}")
	@ResponseStatus(HttpStatus.NO_CONTENT)
	public Success<Void> deleteCommentLike(@PathVariable("id") long id) {
		service.deleteCommentLike(id);
		return new Success<>();
	}
}
