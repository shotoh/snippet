package io.github.shotoh.snippet.services;

import io.github.shotoh.snippet.exceptions.ResourceAlreadyExistsException;
import io.github.shotoh.snippet.exceptions.ResourceNotFoundException;
import io.github.shotoh.snippet.mappers.PostMapper;
import io.github.shotoh.snippet.models.posts.Post;
import io.github.shotoh.snippet.models.posts.PostCreateDTO;
import io.github.shotoh.snippet.models.posts.PostDTO;
import io.github.shotoh.snippet.repositories.PostRepository;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class PostService {
	private final PostRepository repository;
	private final PostMapper mapper;

	private final AuthService authService;

	@Autowired
	public PostService(PostRepository repository, PostMapper mapper, AuthService authService) {
		this.repository = repository;
		this.mapper = mapper;
		this.authService = authService;
	}

	public Post getPost(long id) {
		return repository.findById(id).orElseThrow(() -> new ResourceNotFoundException("id", "Post not found with this id"));
	}

	public List<PostDTO> retrievePosts() {
		return repository.findAll().stream().map(mapper::toDTO).toList();
	}

	public List<PostDTO> retrievePostsByUser(long userId) {
		return repository.findAllByUserId(userId).stream().map(mapper::toDTO).toList();
	}

	public PostDTO createPost(PostCreateDTO postCreateDTO) {
		if (repository.existsById(postCreateDTO.getId())) {
			throw new ResourceAlreadyExistsException("id", "Post already exists with this id");
		}
		authService.check(postCreateDTO.getUserId());
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
