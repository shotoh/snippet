package to.us.snippet.posts;

import to.us.snippet.users.UserService;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingTarget;
import org.mapstruct.NullValuePropertyMappingStrategy;

@Mapper(componentModel = "spring", uses = UserService.class)
public interface PostMapper {
	@Mapping(target = "id", ignore = true)
	@Mapping(source = "userId", target = "user")
	@Mapping(target = "timestamp", ignore = true)
	Post toEntity(PostCreateDTO postCreateDTO);

	PostDTO toDTO(Post post);

	@Mapping(target = "id", ignore = true)
	@Mapping(target = "user", ignore = true)
	@Mapping(target = "content", nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE)
	@Mapping(target = "timestamp", ignore = true)
	void updateEntity(PostDTO postDTO, @MappingTarget Post post);
}
