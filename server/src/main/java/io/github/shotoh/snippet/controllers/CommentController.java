package io.github.shotoh.snippet.controllers;

import io.github.shotoh.snippet.models.comments.CommentCreateDTO;
import io.github.shotoh.snippet.models.comments.CommentDTO;
import io.github.shotoh.snippet.responses.Success;
import io.github.shotoh.snippet.services.CommentService;
import jakarta.validation.Valid;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

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
	public Success<List<CommentDTO>> retrieveComments() {
		return new Success<>(service.retrieveComments());
	}

	@PostMapping
	@ResponseStatus(HttpStatus.CREATED)
	public Success<CommentDTO> createComment(@RequestBody @Valid CommentCreateDTO commentCreateDTO) {
		return new Success<>(service.createComment(commentCreateDTO));
	}

	@GetMapping("/{id}")
	@ResponseStatus(HttpStatus.OK)
	public Success<CommentDTO> retrieveComment(@PathVariable("id") long id) {
		return new Success<>(service.retrieveComment(id));
	}

	@PatchMapping("/{id}")
	@ResponseStatus(HttpStatus.OK)
	public Success<CommentDTO> updateComment(@PathVariable("id") long id, @RequestBody @Valid CommentDTO commentDTO) {
		return new Success<>(service.updateComment(id, commentDTO));
	}

	@DeleteMapping("/{id}")
	@ResponseStatus(HttpStatus.NO_CONTENT)
	public Success<Void> deleteComment(@PathVariable("id") long id) {
		service.deleteComment(id);
		return new Success<>();
	}
}
