package io.github.shotoh.snippet.mappers;

import io.github.shotoh.snippet.models.images.Image;
import io.github.shotoh.snippet.models.images.ImageCreateDTO;
import io.github.shotoh.snippet.models.images.ImageDTO;
import io.github.shotoh.snippet.services.PostService;
import io.github.shotoh.snippet.services.UserService;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(componentModel = "spring", uses = {UserService.class, PostService.class})
public interface ImageMapper {
	@Mapping(target = "id", ignore = true)
	@Mapping(source = "userId", target = "user")
	@Mapping(source = "postId", target = "post")
	@Mapping(target = "timestamp", ignore = true)
	Image toEntity(ImageCreateDTO imageCreateDTO);

	ImageDTO toDTO(Image image);
}
