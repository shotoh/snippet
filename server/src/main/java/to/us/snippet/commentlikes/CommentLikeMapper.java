package to.us.snippet.commentlikes;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import to.us.snippet.comments.CommentService;
import to.us.snippet.posts.PostService;
import to.us.snippet.users.UserService;

@Mapper(componentModel = "spring", uses = {UserService.class, PostService.class, CommentService.class})
public interface CommentLikeMapper {
	@Mapping(target = "id", ignore = true)
	@Mapping(source = "userId", target = "user")
	@Mapping(source = "commentId", target = "comment")
	@Mapping(target = "timestamp", ignore = true)
	CommentLike toEntity(CommentLikeCreateDTO commentLikeCreateDTO);

	CommentLikeDTO toDTO(CommentLike commentLike);
}
