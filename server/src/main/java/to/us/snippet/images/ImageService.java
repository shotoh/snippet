package to.us.snippet.images;

import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import to.us.snippet.auth.AuthService;
import to.us.snippet.exceptions.ResourceNotFoundException;
import to.us.snippet.posts.Post;
import to.us.snippet.posts.PostService;

@Service
public class ImageService {
	private final ImageRepository repository;
	private final ImageMapper mapper;

	private final AuthService authService;
	private final PostService postService;

	@Autowired
	public ImageService(ImageRepository repository, ImageMapper mapper, AuthService authService, PostService postService) {
		this.repository = repository;
		this.mapper = mapper;
		this.authService = authService;
		this.postService = postService;
	}

	public Image getImage(long id) {
		return repository.findById(id).orElseThrow(() -> new ResourceNotFoundException("id", "Image not found with this id"));
	}

	public ImageDTO retrieveImageByUser(long userId) {
		return mapper.toDTO(repository.findFirstByUserIdAndPostNull(userId));
	}

	public List<ImageDTO> retrieveImagesByPost(long postId) {
		return repository.findAllByPostId(postId).stream().map(mapper::toDTO).toList();
	}

	public ImageDTO createImage(ImageCreateDTO imageCreateDTO) {
		long postId = imageCreateDTO.getPostId();
		if (postId != 0) {
			Post post = postService.getPost(postId);
			authService.check(post);
		}
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
