package to.us.snippet.mappers;

import to.us.snippet.models.medias.Media;
import to.us.snippet.models.medias.MediaCreateDTO;
import to.us.snippet.models.medias.MediaDTO;
import to.us.snippet.posts.PostService;
import to.us.snippet.users.UserService;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingTarget;
import org.mapstruct.NullValuePropertyMappingStrategy;

@Mapper(componentModel = "spring", uses = {UserService.class, PostService.class})
public interface MediaMapper {
	@Mapping(source = "postId", target = "post")
	@Mapping(target = "timestamp", ignore = true)
	Media toEntity(MediaCreateDTO mediaCreateDTO);

	MediaDTO toDTO(Media media);

	@Mapping(target = "id", ignore = true)
	@Mapping(target = "post", ignore = true)
	@Mapping(target = "source", nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE)
	@Mapping(target = "timestamp", ignore = true)
	void updateEntity(MediaDTO mediaDTO, @MappingTarget Media media);
}
