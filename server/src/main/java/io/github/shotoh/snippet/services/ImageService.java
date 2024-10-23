package io.github.shotoh.snippet.services;

import io.github.shotoh.snippet.exceptions.ResourceNotFoundException;
import io.github.shotoh.snippet.mappers.ImageMapper;
import io.github.shotoh.snippet.models.images.Image;
import io.github.shotoh.snippet.models.images.ImageCreateDTO;
import io.github.shotoh.snippet.models.images.ImageDTO;
import io.github.shotoh.snippet.repositories.ImageRepository;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class ImageService {
	private final ImageRepository repository;
	private final ImageMapper mapper;

	private final AuthService authService;

	@Autowired
	public ImageService(ImageRepository repository, ImageMapper mapper, AuthService authService) {
		this.repository = repository;
		this.mapper = mapper;
		this.authService = authService;
	}

	public Image getImage(long id) {
		return repository.findById(id).orElseThrow(() -> new ResourceNotFoundException("id", "Image not found with this id"));
	}

	public ImageDTO retrieveImageByUser(long userId) {
		return mapper.toDTO(repository.findFirstByUserId(userId));
	}

	public List<ImageDTO> retrieveImagesByPost(long postId) {
		return repository.findAllByPostId(postId).stream().map(mapper::toDTO).toList();
	}

	public ImageDTO createImage(ImageCreateDTO imageCreateDTO) {
		imageCreateDTO.setUserId(authService.userId());
		Image image = repository.save(mapper.toEntity(imageCreateDTO));
		return mapper.toDTO(image);
	}

	public ImageDTO retrieveImage(long id) {
		Image image = getImage(id);
		return mapper.toDTO(image);
	}

	public void deleteImage(long id) {
		Image image = getImage(id);
		authService.check(image);
		repository.deleteById(image.getId());
	}
}
