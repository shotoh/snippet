package to.us.snippet.posts;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingTarget;
import org.mapstruct.NullValuePropertyMappingStrategy;
import to.us.snippet.users.UserService;

@Mapper(componentModel = "spring", uses = {UserService.class, PostService.class})
public interface PostMapper {
	@Mapping(target = "id", ignore = true)
	@Mapping(source = "userId", target = "user")
	@Mapping(target = "images", ignore = true)
	@Mapping(target = "timestamp", ignore = true)
	Post toEntity(PostCreateDTO postCreateDTO);

	@Mapping(target = "images", qualifiedByName = "getPostImage")
	PostDTO toDTO(Post post);

	@Mapping(target = "id", ignore = true)
	@Mapping(target = "user", ignore = true)
	@Mapping(target = "content", nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE)
	@Mapping(target = "images", ignore = true)
	@Mapping(target = "timestamp", ignore = true)
	void updateEntity(PostDTO postDTO, @MappingTarget Post post);
}
