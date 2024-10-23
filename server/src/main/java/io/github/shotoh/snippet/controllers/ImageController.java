package io.github.shotoh.snippet.controllers;

import io.github.shotoh.snippet.exceptions.UnauthorizedException;
import io.github.shotoh.snippet.models.images.ImageCreateDTO;
import io.github.shotoh.snippet.models.images.ImageDTO;
import io.github.shotoh.snippet.responses.Success;
import io.github.shotoh.snippet.services.ImageService;
import jakarta.validation.Valid;
import java.util.List;
import java.util.Optional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping(path = "/api/images")
@PreAuthorize("hasRole('USER')")
public class ImageController {
	private final ImageService service;

	@Autowired
	public ImageController(ImageService service) {
		this.service = service;
	}

	@GetMapping
	@ResponseStatus(HttpStatus.OK)
	public Success<List<ImageDTO>> retrieveImages(@RequestParam(name = "user") Optional<Long> userId,
	                                              @RequestParam(name = "post") Optional<Long> postId) {
		if (userId.isPresent()) {
			return new Success<>(List.of(service.retrieveImageByUser(userId.get())));
		} else if (postId.isPresent()) {
			return new Success<>(service.retrieveImagesByPost(postId.get()));
		} else {
			throw new UnauthorizedException();
		}
	}

	@PostMapping
	@ResponseStatus(HttpStatus.CREATED)
	public Success<ImageDTO> createImage(@RequestBody @Valid ImageCreateDTO imageCreateDTO) {
		return new Success<>(service.createImage(imageCreateDTO));
	}

	@GetMapping("/{id}")
	@ResponseStatus(HttpStatus.OK)
	public Success<ImageDTO> retrieveImage(@PathVariable("id") long id) {
		return new Success<>(service.retrieveImage(id));
	}

	@DeleteMapping("/{id}")
	@ResponseStatus(HttpStatus.NO_CONTENT)
	public Success<Void> deleteImage(@PathVariable("id") long id) {
		service.deleteImage(id);
		return new Success<>();
	}
}
