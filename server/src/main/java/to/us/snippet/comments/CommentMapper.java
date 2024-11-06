package to.us.snippet.comments;

import org.mapstruct.AfterMapping;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingTarget;
import org.mapstruct.NullValuePropertyMappingStrategy;
import org.springframework.beans.factory.annotation.Autowired;
import to.us.snippet.auth.AuthService;
import to.us.snippet.commentlikes.CommentLike;
import to.us.snippet.commentlikes.CommentLikeRepository;
import to.us.snippet.posts.PostService;
import to.us.snippet.users.UserService;

@Mapper(componentModel = "spring", uses = {UserService.class, PostService.class})
public abstract class CommentMapper {
	@Autowired
	AuthService authService;
	@Autowired
	CommentLikeRepository repository;

	@Mapping(target = "id", ignore = true)
	@Mapping(source = "userId", target = "user")
	@Mapping(source = "postId", target = "post")
	@Mapping(target = "timestamp", ignore = true)
	public abstract Comment toEntity(CommentCreateDTO commentCreateDTO);

	@Mapping(target = "post.liked", ignore = true)
	@Mapping(target = "post.totalLikes", ignore = true)
	@Mapping(target = "post.totalDislikes", ignore = true)
	@Mapping(target = "liked", ignore = true)
	@Mapping(target = "totalLikes", ignore = true)
	@Mapping(target = "totalDislikes", ignore = true)
	public abstract CommentDTO toDTO(Comment comment);

	@Mapping(target = "id", ignore = true)
	@Mapping(target = "user", ignore = true)
	@Mapping(target = "post", ignore = true)
	@Mapping(target = "content", nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE)
	@Mapping(target = "timestamp", ignore = true)
	public abstract void updateEntity(CommentDTO commentDTO, @MappingTarget Comment comment);

	@AfterMapping
	protected void setLikes(Comment comment, @MappingTarget CommentDTO commentDTO) {
		CommentLike commentLike = repository.getCommentLikeByUserIdAndCommentId(authService.userId(), comment.getId());
		if (commentLike != null) {
			commentDTO.setLiked(commentLike.getValue());
		} else {
			commentDTO.setLiked(0);
		}
		commentDTO.setTotalLikes(repository.countAllByCommentIdAndValue(comment.getId(), 1));
		commentDTO.setTotalDislikes(repository.countAllByCommentIdAndValue(comment.getId(), -1));
	}
}
