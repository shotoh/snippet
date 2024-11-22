package to.us.snippet.messages;

import jakarta.validation.Valid;
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
import to.us.snippet.responses.Response;
import to.us.snippet.responses.ResponseBuilder;
import to.us.snippet.responses.Status;

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
	public Response retrieveMessages(@RequestParam(name = "from") long fromId, @RequestParam(name = "to") long toId) {
		return new ResponseBuilder(Status.SUCCESS)
				.setData(service.retrieveMessagesByFromAndTo(fromId, toId))
				.build();
	}

	@PostMapping
	@ResponseStatus(HttpStatus.CREATED)
	public Response createMessage(@RequestBody @Valid MessageCreateDTO messageCreateDTO) {
		return new ResponseBuilder(Status.SUCCESS)
				.setData(service.createMessage(messageCreateDTO))
				.build();
	}

	@PatchMapping("/{id}")
	@ResponseStatus(HttpStatus.OK)
	public Response updateMessage(@PathVariable("id") long id, @RequestBody @Valid MessageDTO messageDTO) {
		return new ResponseBuilder(Status.SUCCESS)
				.setData(service.updateMessage(id, messageDTO))
				.build();
	}

	@DeleteMapping("/{id}")
	@ResponseStatus(HttpStatus.NO_CONTENT)
	public Response deleteMessage(@PathVariable("id") long id) {
		service.deleteMessage(id);
		return new ResponseBuilder(Status.SUCCESS).build();
	}
}
