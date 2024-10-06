package io.github.shotoh.snippet.services;

import io.github.shotoh.snippet.exceptions.ResourceAlreadyExistsException;
import io.github.shotoh.snippet.exceptions.ResourceNotFoundException;
import io.github.shotoh.snippet.mappers.PostLikeMapper;
import io.github.shotoh.snippet.models.postlikes.PostLike;
import io.github.shotoh.snippet.models.postlikes.PostLikeCreateDTO;
import io.github.shotoh.snippet.models.postlikes.PostLikeDTO;
import io.github.shotoh.snippet.repositories.PostLikeRepository;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class PostLikeService {
	private final PostLikeRepository repository;
	private final PostLikeMapper mapper;

	private final AuthService authService;

	@Autowired
	public PostLikeService(PostLikeRepository repository, PostLikeMapper mapper, AuthService authService) {
		this.repository = repository;
		this.mapper = mapper;
		this.authService = authService;
	}

	public PostLike getPostLike(long id) {
		return repository.findById(id).orElseThrow(() -> new ResourceNotFoundException("id", "Like not found with this id"));
	}

	public List<PostLikeDTO> retrievePostLikes() {
		return repository.findAll().stream().map(mapper::toDTO).toList();
	}

	public List<PostLikeDTO> retrievePostLikesByPost(long postId) {
		return repository.findAllByPostId(postId).stream().map(mapper::toDTO).toList();
	}

	public PostLikeDTO retrievePostLikeByUserAndPost(long userId, long postId) {
		return mapper.toDTO(repository.findPostLikeByUserIdAndPostId(userId, postId));
	}

	public PostLikeDTO createPostLike(PostLikeCreateDTO postLikeCreateDTO) {
		postLikeCreateDTO.setUserId(authService.userId());
		if (repository.existsByUserIdAndPostId(postLikeCreateDTO.getUserId(), postLikeCreateDTO.getPostId())) {
			throw new ResourceAlreadyExistsException("userId", "Like already exists with this user and post id");
		}
		PostLike postLike = repository.save(mapper.toEntity(postLikeCreateDTO));
		return mapper.toDTO(postLike);
	}

	public PostLikeDTO retrievePostLike(long id) {
		PostLike postLike = getPostLike(id);
		return mapper.toDTO(postLike);
	}

	public void deletePostLike(long id) {
		PostLike postLike = getPostLike(id);
		authService.check(postLike);
		repository.deleteById(postLike.getId());
	}
}
