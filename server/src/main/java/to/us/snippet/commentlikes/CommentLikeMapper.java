package to.us.snippet.commentlikes;

import to.us.snippet.comments.CommentService;
import to.us.snippet.users.UserService;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(componentModel = "spring", uses = {UserService.class, CommentService.class})
public interface CommentLikeMapper {
	@Mapping(target = "id", ignore = true)
	@Mapping(source = "userId", target = "user")
	@Mapping(source = "commentId", target = "comment")
	@Mapping(target = "timestamp", ignore = true)
	CommentLike toEntity(CommentLikeCreateDTO commentLikeCreateDTO);

	CommentLikeDTO toDTO(CommentLike commentLike);
}
