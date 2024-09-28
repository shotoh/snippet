package io.github.shotoh.snippet.controllers;

import io.github.shotoh.snippet.models.posts.PostCreateDTO;
import io.github.shotoh.snippet.models.posts.PostDTO;
import io.github.shotoh.snippet.responses.Success;
import io.github.shotoh.snippet.services.PostService;
import jakarta.validation.Valid;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping(path = "/api/posts")
@PreAuthorize("hasRole('ADMIN')")
public class PostController {
	private final PostService service;

	@Autowired
	public PostController(PostService service) {
		this.service = service;
	}

	@GetMapping
	@ResponseStatus(HttpStatus.OK)
	public Success<List<PostDTO>> retrievePosts() {
		return new Success<>(service.retrievePosts());
	}

	@PostMapping
	@ResponseStatus(HttpStatus.CREATED)
	public Success<PostDTO> createPost(@RequestBody @Valid PostCreateDTO postCreateDTO) {
		return new Success<>(service.createPost(postCreateDTO));
	}

	@GetMapping("/{id}")
	@ResponseStatus(HttpStatus.OK)
	public Success<PostDTO> retrievePost(@PathVariable("id") long id) {
		return new Success<>(service.retrievePost(id));
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
