package to.us.snippet.comments;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingTarget;
import org.mapstruct.NullValuePropertyMappingStrategy;
import to.us.snippet.posts.PostService;
import to.us.snippet.users.UserService;

@Mapper(componentModel = "spring", uses = {UserService.class, PostService.class})
public interface CommentMapper {
	@Mapping(target = "id", ignore = true)
	@Mapping(source = "userId", target = "user")
	@Mapping(source = "postId", target = "post")
	@Mapping(target = "timestamp", ignore = true)
	Comment toEntity(CommentCreateDTO commentCreateDTO);

	CommentDTO toDTO(Comment comment);

	@Mapping(target = "id", ignore = true)
	@Mapping(target = "user", ignore = true)
	@Mapping(target = "post", ignore = true)
	@Mapping(target = "content", nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE)
	@Mapping(target = "timestamp", ignore = true)
	void updateEntity(CommentDTO commentDTO, @MappingTarget Comment comment);
}
