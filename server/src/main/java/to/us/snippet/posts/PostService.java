package to.us.snippet.posts;

import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;
import to.us.snippet.auth.AuthService;
import to.us.snippet.exceptions.ResourceNotFoundException;
import to.us.snippet.images.ImageService;
import to.us.snippet.images.PostImage;
import to.us.snippet.images.PostImageRepository;

@Service
public class PostService {
	private final PostRepository repository;
	private final PostImageRepository imageRepository;
	private final PostMapper mapper;

	private final AuthService authService;
	private final ImageService imageService;

	@Autowired
	public PostService(PostRepository repository, PostImageRepository imageRepository,
	                   PostMapper mapper, AuthService authService, ImageService imageService) {
		this.repository = repository;
		this.imageRepository = imageRepository;
		this.mapper = mapper;
		this.authService = authService;
		this.imageService = imageService;
	}

	public Post getPost(long id) {
		return repository.findById(id).orElseThrow(() -> new ResourceNotFoundException("id", "Post not found with this id"));
	}

	public List<String> getPostImages(List<PostImage> images) {
		if (images == null) return List.of();
		return images.stream().map(PostImage::getContent).toList();
	}

	public List<PostDTO> retrievePosts() {
		return repository.findAll().stream().map(mapper::toDTO).toList();
	}

	public List<PostDTO> retrievePostsByUser(long userId) {
		return repository.findAllByUserId(userId).stream().map(mapper::toDTO).toList();
	}

	public PostDTO createPost(PostCreateDTO postCreateDTO) {
		postCreateDTO.setUserId(authService.userId());
		Post post = repository.save(mapper.toEntity(postCreateDTO));
		return mapper.toDTO(post);
	}

	public PostDTO retrievePost(long id) {
		Post post = getPost(id);
		return mapper.toDTO(post);
	}

	public PostDTO updatePost(long id, PostDTO postDTO) {
		Post post = getPost(id);
		authService.check(post);
		mapper.updateEntity(postDTO, post);
		repository.save(post);
		return mapper.toDTO(post);
	}

	public void addPostPicture(long id, MultipartFile file) {
		Post post = getPost(id);
		authService.check(post);
		String url = imageService.saveImage(file);
		PostImage postImage = new PostImage();
		postImage.setPost(post);
		postImage.setContent(url);
		imageRepository.save(postImage);
		repository.save(post);
	}

	public void deletePost(long id) {
		Post post = getPost(id);
		authService.check(post);
		for (PostImage postImage : post.getImages()) {
			imageService.deleteImage(postImage.getContent());
		}
		repository.deleteById(post.getId());
	}
}
