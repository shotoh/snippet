package io.github.shotoh.snippet.services;

import io.github.shotoh.snippet.exceptions.ResourceNotFoundException;
import io.github.shotoh.snippet.mappers.MessageMapper;
import io.github.shotoh.snippet.models.messages.Message;
import io.github.shotoh.snippet.models.messages.MessageCreateDTO;
import io.github.shotoh.snippet.models.messages.MessageDTO;
import io.github.shotoh.snippet.repositories.MessageRepository;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class MessageService {
	private final MessageRepository repository;
	private final MessageMapper mapper;

	private final AuthService authService;

	@Autowired
	public MessageService(MessageRepository repository, MessageMapper mapper, AuthService authService) {
		this.repository = repository;
		this.mapper = mapper;
		this.authService = authService;
	}

	public Message getMessage(long id) {
		return repository.findById(id).orElseThrow(() -> new ResourceNotFoundException("id", "Message not found with this id"));
	}

	public List<MessageDTO> retrieveMessagesByFromAndTo(long fromId, long toId) {
		return repository.findAllByFromIdAndToId(fromId, toId).stream().map(mapper::toDTO).toList();
	}

	public MessageDTO createMessage(MessageCreateDTO messageCreateDTO) {
		messageCreateDTO.setFromId(authService.userId());
		Message message = repository.save(mapper.toEntity(messageCreateDTO));
		return mapper.toDTO(message);
	}

	public MessageDTO updateMessage(long id, MessageDTO messageDTO) {
		Message message = getMessage(id);
		authService.check(message);
		mapper.updateEntity(messageDTO, message);
		repository.save(message);
		return mapper.toDTO(message);
	}

	public void deleteMessage(long id) {
		Message message = getMessage(id);
		authService.check(message);
		repository.deleteById(message.getId());
	}
}
