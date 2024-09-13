package io.github.shotoh.uzi.mappers;

import io.github.shotoh.uzi.models.posts.Post;
import io.github.shotoh.uzi.models.posts.PostDTO;
import io.github.shotoh.uzi.services.UserService;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingTarget;
import org.mapstruct.NullValuePropertyMappingStrategy;

@Mapper(componentModel = "spring", uses = UserService.class)
public interface PostMapper {
    @Mapping(source = "userId", target = "user")
    @Mapping(target = "timestamp", ignore = true)
    Post toEntity(PostDTO postDTO);

    @Mapping(source = "user", target = "userId")
    PostDTO toDTO (Post post);

    @Mapping(target = "id", ignore = true)
    @Mapping(source = "userId", target = "user")
    @Mapping(target = "title", nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE)
    @Mapping(target = "content", nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE)
    @Mapping(target = "timestamp", ignore = true)
    void updateEntity(PostDTO postDTO, @MappingTarget Post post);
}
