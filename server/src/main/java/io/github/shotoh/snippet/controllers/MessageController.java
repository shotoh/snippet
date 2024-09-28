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
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping(path = "/api/messages")
@PreAuthorize("hasRole('ADMIN')")
public class MessageController {
	private final MessageService service;

	@Autowired
	public MessageController(MessageService service) {
		this.service = service;
	}

	@GetMapping
	@ResponseStatus(HttpStatus.OK)
	public Success<List<MessageDTO>> retrieveMessages() {
		return new Success<>(service.retrieveMessages());
	}

	@PostMapping
	@ResponseStatus(HttpStatus.CREATED)
	public Success<MessageDTO> createMessage(@RequestBody @Valid MessageCreateDTO messageCreateDTO) {
		return new Success<>(service.createMessage(messageCreateDTO));
	}

	@GetMapping("/{id}")
	@ResponseStatus(HttpStatus.OK)
	public Success<MessageDTO> retrieveMessage(@PathVariable("id") long id) {
		return new Success<>(service.retrieveMessage(id));
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
