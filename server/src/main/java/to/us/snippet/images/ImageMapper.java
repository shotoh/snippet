package to.us.snippet.images;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import to.us.snippet.posts.PostService;

@Mapper(componentModel = "spring", uses = {PostService.class})
public interface ImageMapper {
	@Mapping(target = "id", ignore = true)
	@Mapping(source = "postId", target = "post")
	@Mapping(target = "timestamp", ignore = true)
	Image toEntity(ImageCreateDTO imageCreateDTO);

	ImageDTO toDTO(Image image);
}
