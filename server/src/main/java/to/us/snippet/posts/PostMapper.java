package to.us.snippet.posts;

import java.util.List;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingTarget;
import org.mapstruct.Named;
import org.mapstruct.NullValuePropertyMappingStrategy;
import to.us.snippet.images.PostImage;
import to.us.snippet.users.UserService;

@Mapper(componentModel = "spring", uses = {UserService.class})
public interface PostMapper {
	@Mapping(target = "id", ignore = true)
	@Mapping(source = "userId", target = "user")
	@Mapping(target = "images", ignore = true)
	@Mapping(target = "timestamp", ignore = true)
	Post toEntity(PostCreateDTO postCreateDTO);

	@Mapping(target = "images", qualifiedByName = "getPostImages")
	PostDTO toDTO(Post post);

	@Mapping(target = "id", ignore = true)
	@Mapping(target = "user", ignore = true)
	@Mapping(target = "content", nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE)
	@Mapping(target = "images", ignore = true)
	@Mapping(target = "timestamp", ignore = true)
	void updateEntity(PostDTO postDTO, @MappingTarget Post post);

	@Named("getPostImages")
	default List<String> getPostImages(List<PostImage> images) {
		if (images == null) return List.of();
		return images.stream().map(PostImage::getContent).toList();
	}
}
