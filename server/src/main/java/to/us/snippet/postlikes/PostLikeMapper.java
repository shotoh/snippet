package to.us.snippet.postlikes;

import to.us.snippet.posts.PostService;
import to.us.snippet.users.UserService;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(componentModel = "spring", uses = {UserService.class, PostService.class})
public interface PostLikeMapper {
	@Mapping(target = "id", ignore = true)
	@Mapping(source = "userId", target = "user")
	@Mapping(source = "postId", target = "post")
	@Mapping(target = "timestamp", ignore = true)
	PostLike toEntity(PostLikeCreateDTO postLikeCreateDTO);

	PostLikeDTO toDTO(PostLike postLike);
}
