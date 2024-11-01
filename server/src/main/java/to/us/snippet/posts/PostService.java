package to.us.snippet.posts;

import java.util.List;
import org.mapstruct.Named;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import to.us.snippet.auth.AuthService;
import to.us.snippet.exceptions.ResourceNotFoundException;
import to.us.snippet.images.ImageService;
import to.us.snippet.images.PostImage;
import to.us.snippet.images.PostImageMapper;
import to.us.snippet.images.PostImageRepository;

@Service
public class PostService {
	private final PostRepository repository;
	private final PostImageRepository imageRepository;
	private final PostMapper mapper;
	private final PostImageMapper imageMapper;

	private final AuthService authService;
	private final ImageService imageService;

	@Autowired
	public PostService(PostRepository repository, PostImageRepository imageRepository,
	                   PostMapper mapper, PostImageMapper imageMapper,
	                   AuthService authService, ImageService imageService) {
		this.repository = repository;
		this.imageRepository = imageRepository;
		this.mapper = mapper;
		this.imageMapper = imageMapper;
		this.authService = authService;
		this.imageService = imageService;
	}

	public Post getPost(long id) {
		return repository.findById(id).orElseThrow(() -> new ResourceNotFoundException("id", "Post not found with this id"));
	}

	@Named("getPostImage")
	public PostImage getPostImage(long id) {
		return imageRepository.findById(id).orElseThrow(() -> new ResourceNotFoundException("id", "Image not found with this id"));
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

	public void deletePost(long id) {
		Post post = getPost(id);
		authService.check(post);
		repository.deleteById(post.getId());
	}
}
