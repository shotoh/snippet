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

	@Autowired
	public CommentLikeService(CommentLikeRepository repository, CommentLikeMapper mapper) {
		this.repository = repository;
		this.mapper = mapper;
	}

	public CommentLike getCommentLike(long id) {
		return repository.findById(id).orElseThrow(() -> new ResourceNotFoundException("id", "Like not found with this id"));
	}

	public List<CommentLikeDTO> retrieveCommentLikes() {
		return repository.findAll().stream().map(mapper::toDTO).toList();
	}

	public CommentLikeDTO createCommentLike(CommentLikeCreateDTO commentLikeCreateDTO) {
		if (repository.existsById(commentLikeCreateDTO.getId())) {
			throw new ResourceAlreadyExistsException("id", "Like already exists with this id");
		}
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
		repository.deleteById(id);
	}
}
