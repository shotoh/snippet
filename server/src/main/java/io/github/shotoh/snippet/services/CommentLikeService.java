package io.github.shotoh.snippet.services;

import io.github.shotoh.snippet.exceptions.ResourceAlreadyExistsException;
import io.github.shotoh.snippet.exceptions.ResourceNotFoundException;
import io.github.shotoh.snippet.mappers.CommentLikeMapper;
import io.github.shotoh.snippet.models.commentlikes.CommentLike;
import io.github.shotoh.snippet.models.commentlikes.CommentLikeCreateDTO;
import io.github.shotoh.snippet.models.commentlikes.CommentLikeDTO;
import io.github.shotoh.snippet.repositories.CommentLikeRepository;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class CommentLikeService {
	private final CommentLikeRepository repository;
	private final CommentLikeMapper mapper;

	private final AuthService authService;

	@Autowired
	public CommentLikeService(CommentLikeRepository repository, CommentLikeMapper mapper, AuthService authService) {
		this.repository = repository;
		this.mapper = mapper;
		this.authService = authService;
	}

	public CommentLike getCommentLike(long id) {
		return repository.findById(id).orElseThrow(() -> new ResourceNotFoundException("id", "Like not found with this id"));
	}

	public List<CommentLikeDTO> retrieveCommentLikesByComment(long commentId) {
		return repository.findAllByCommentId(commentId).stream().map(mapper::toDTO).toList();
	}

	public CommentLikeDTO retrieveCommentLikeByUserAndComment(long userId, long commentId) {
		CommentLike commentLike = repository.findCommentLikeByUserIdAndCommentId(userId, commentId);
		authService.check(commentLike);
		return mapper.toDTO(commentLike);
	}

	public CommentLikeDTO createCommentLike(CommentLikeCreateDTO commentLikeCreateDTO) {
		commentLikeCreateDTO.setUserId(authService.userId());
		if (repository.existsByUserIdAndCommentId(commentLikeCreateDTO.getUserId(), commentLikeCreateDTO.getCommentId())) {
			throw new ResourceAlreadyExistsException("userId", "Like already exists with this user and comment id");
		}
		CommentLike commentLike = repository.save(mapper.toEntity(commentLikeCreateDTO));
		return mapper.toDTO(commentLike);
	}

	public CommentLikeDTO retrieveCommentLike(long id) {
		CommentLike commentLike = getCommentLike(id);
		return mapper.toDTO(commentLike);
	}

	public void deleteCommentLike(long id) {
		CommentLike commentLike = getCommentLike(id);
		authService.check(commentLike);
		repository.deleteById(commentLike.getId());
	}
}
