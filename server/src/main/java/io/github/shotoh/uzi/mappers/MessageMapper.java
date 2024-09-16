package io.github.shotoh.uzi.mappers;

import io.github.shotoh.uzi.models.messages.Message;
import io.github.shotoh.uzi.models.messages.MessageCreateDTO;
import io.github.shotoh.uzi.models.messages.MessageDTO;
import io.github.shotoh.uzi.services.PostService;
import io.github.shotoh.uzi.services.UserService;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingTarget;
import org.mapstruct.NullValuePropertyMappingStrategy;

@Mapper(componentModel = "spring", uses = { UserService.class, PostService.class })
public interface MessageMapper {
    @Mapping(source = "fromId", target = "from")
    @Mapping(source = "toId", target = "to")
    @Mapping(target = "timestamp", ignore = true)
    Message toEntity(MessageCreateDTO messageCreateDTO);

    MessageDTO toDTO(Message message);

    @Mapping(target = "id", ignore = true)
    @Mapping(target = "from", ignore = true)
    @Mapping(target = "to", ignore = true)
    @Mapping(target = "content", nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE)
    @Mapping(target = "timestamp", ignore = true)
    void updateEntity(MessageDTO messageDTO, @MappingTarget Message message);
}
