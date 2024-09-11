package io.github.shotoh.uzi.mappers;

import io.github.shotoh.uzi.models.posts.Post;
import io.github.shotoh.uzi.models.posts.PostDTO;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingTarget;
import org.mapstruct.NullValuePropertyMappingStrategy;

@Mapper(componentModel = "spring")
public interface PostMapper {
    @Mapping(target = "timestamp", ignore = true)
    Post toEntity(PostDTO postDTO);
    PostDTO toDTO (Post post);

    @Mapping(target = "id", ignore = true)
    @Mapping(target = "title", nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE)
    @Mapping(target = "content", nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE)
    @Mapping(target = "timestamp", ignore = true)
    void updateEntity(PostDTO postDTO, @MappingTarget Post post);
}
