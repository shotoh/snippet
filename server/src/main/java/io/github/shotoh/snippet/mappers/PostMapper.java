package io.github.shotoh.snippet.mappers;

import io.github.shotoh.snippet.models.posts.Post;
import io.github.shotoh.snippet.models.posts.PostCreateDTO;
import io.github.shotoh.snippet.models.posts.PostDTO;
import io.github.shotoh.snippet.services.UserService;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingTarget;
import org.mapstruct.NullValuePropertyMappingStrategy;

@Mapper(componentModel = "spring", uses = UserService.class)
public interface PostMapper {
    @Mapping(source = "userId", target = "user")
    @Mapping(target = "timestamp", ignore = true)
    Post toEntity(PostCreateDTO postCreateDTO);

    PostDTO toDTO(Post post);

    @Mapping(target = "id", ignore = true)
    @Mapping(target = "user", ignore = true)
    @Mapping(target = "title", nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE)
    @Mapping(target = "content", nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE)
    @Mapping(target = "timestamp", ignore = true)
    void updateEntity(PostDTO postDTO, @MappingTarget Post post);
}
