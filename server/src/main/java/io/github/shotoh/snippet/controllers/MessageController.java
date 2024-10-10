package io.github.shotoh.snippet.controllers;

import io.github.shotoh.snippet.models.messages.MessageCreateDTO;
import io.github.shotoh.snippet.models.messages.MessageDTO;
import io.github.shotoh.snippet.responses.Success;
import io.github.shotoh.snippet.services.MessageService;
import jakarta.validation.Valid;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping(path = "/api/messages")
@PreAuthorize("hasRole('USER')")
public class MessageController {
	private final MessageService service;

	@Autowired
	public MessageController(MessageService service) {
		this.service = service;
	}

	@GetMapping
	@ResponseStatus(HttpStatus.OK)
	public Success<List<MessageDTO>> retrieveMessages(@RequestParam(name = "from") long fromId, @RequestParam(name = "to") long toId) {
		return new Success<>(service.retrieveMessagesByFromAndTo(fromId, toId));
	}

	@PostMapping
	@ResponseStatus(HttpStatus.CREATED)
	public Success<MessageDTO> createMessage(@RequestBody @Valid MessageCreateDTO messageCreateDTO) {
		return new Success<>(service.createMessage(messageCreateDTO));
	}

	@PatchMapping("/{id}")
	@ResponseStatus(HttpStatus.OK)
	public Success<MessageDTO> updateMessage(@PathVariable("id") long id, @RequestBody @Valid MessageDTO messageDTO) {
		return new Success<>(service.updateMessage(id, messageDTO));
	}

	@DeleteMapping("/{id}")
	@ResponseStatus(HttpStatus.NO_CONTENT)
	public Success<Void> deleteMessage(@PathVariable("id") long id) {
		service.deleteMessage(id);
		return new Success<>();
	}
}
