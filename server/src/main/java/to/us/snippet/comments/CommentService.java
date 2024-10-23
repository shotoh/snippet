package to.us.snippet.comments;

import to.us.snippet.exceptions.ResourceNotFoundException;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import to.us.snippet.security.AuthService;

@Service
public class CommentService {
	private final CommentRepository repository;
	private final CommentMapper mapper;

	private final AuthService authService;

	@Autowired
	public CommentService(CommentRepository repository, CommentMapper mapper, AuthService authService) {
		this.repository = repository;
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

	public void deleteComment(long id) {
		Comment comment = getComment(id);
		authService.check(comment);
		repository.deleteById(comment.getId());
	}
}
