package to.us.snippet.comments;

import jakarta.transaction.Transactional;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import to.us.snippet.auth.AuthService;
import to.us.snippet.commentlikes.CommentLike;
import to.us.snippet.commentlikes.CommentLikeRepository;
import to.us.snippet.exceptions.ResourceNotFoundException;
import to.us.snippet.users.User;

@Service
public class CommentService {
	private final CommentRepository repository;
	private final CommentLikeRepository likeRepository;
	private final CommentMapper mapper;

	private final AuthService authService;

	@Autowired
	public CommentService(CommentRepository repository, CommentLikeRepository likeRepository,
	                      CommentMapper mapper, AuthService authService) {
		this.repository = repository;
		this.likeRepository = likeRepository;
		this.mapper = mapper;
		this.authService = authService;
	}

	public Comment getComment(long id) {
		return repository.findById(id).orElseThrow(() -> new ResourceNotFoundException("id", "Comment not found with this id"));
	}

	public List<CommentDTO> retrieveComments() {
		return repository.findAll().stream().map(mapper::toDTO).toList();
	}

	public List<CommentDTO> retrieveCommentsByPost(long postId) {
		return repository.findAllByPostId(postId).stream().map(mapper::toDTO).toList();
	}

	public CommentDTO createComment(CommentCreateDTO commentCreateDTO) {
		commentCreateDTO.setUserId(authService.userId());
		Comment comment = repository.save(mapper.toEntity(commentCreateDTO));
		return mapper.toDTO(comment);
	}

	public CommentDTO retrieveComment(long id) {
		Comment comment = getComment(id);
		return mapper.toDTO(comment);
	}

	public CommentDTO updateComment(long id, CommentDTO commentDTO) {
		Comment comment = getComment(id);
		authService.check(comment);
		mapper.updateEntity(commentDTO, comment);
		repository.save(comment);
		return mapper.toDTO(comment);
	}

	@Transactional
	public void likeComment(long id) {
		User user = authService.getUser();
		Comment comment = getComment(id);
		unlikeComment(id);
		CommentLike commentLike = new CommentLike();
		commentLike.setUser(user);
		commentLike.setComment(comment);
		commentLike.setValue(1);
		likeRepository.save(commentLike);
	}

	@Transactional
	public void dislikeComment(long id) {
		User user = authService.getUser();
		Comment comment = getComment(id);
		unlikeComment(id);
		CommentLike commentLike = new CommentLike();
		commentLike.setUser(user);
		commentLike.setComment(comment);
		commentLike.setValue(-1);
		likeRepository.save(commentLike);
	}

	@Transactional
	public void unlikeComment(long id) {
		User user = authService.getUser();
		Comment comment = getComment(id);
		likeRepository.deleteCommentLikeByUserIdAndCommentId(user.getId(), comment.getId());
	}

	public void deleteComment(long id) {
		Comment comment = getComment(id);
		authService.check(comment);
		repository.deleteById(comment.getId());
	}
}
