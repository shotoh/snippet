package to.us.snippet.users;

import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;
import to.us.snippet.responses.Response;
import to.us.snippet.responses.ResponseBuilder;
import to.us.snippet.responses.Status;

@RestController
@RequestMapping(path = "/api/users")
@PreAuthorize("hasRole('USER')")
public class UserController {
	private final UserService service;

	@Autowired
	public UserController(UserService service) {
		this.service = service;
	}

	@GetMapping
	@ResponseStatus(HttpStatus.OK)
	public Response retrieveUsers() {
		return new ResponseBuilder(Status.SUCCESS)
				.setData(service.retrieveUsers())
				.build();
	}

	@GetMapping("/{id}")
	@ResponseStatus(HttpStatus.OK)
	public Response retrieveUser(@PathVariable("id") long id) {
		return new ResponseBuilder(Status.SUCCESS)
				.setData(service.retrieveUser(id))
				.build();
	}

	@PatchMapping("/{id}")
	@ResponseStatus(HttpStatus.OK)
	public Response updateUser(@PathVariable("id") long id, @RequestBody @Valid UserDTO userDTO) {
		return new ResponseBuilder(Status.SUCCESS)
				.setData(service.updateUser(id, userDTO))
				.build();
	}

	@PatchMapping("/{id}/profile-picture")
	@ResponseStatus(HttpStatus.NO_CONTENT)
	public Response updateUserProfilePicture(@PathVariable("id") long id, @RequestParam("file") MultipartFile file) {
		service.updateUserProfilePicture(id, file);
		return new ResponseBuilder(Status.SUCCESS).build();
	}

	@DeleteMapping("/{id}")
	@ResponseStatus(HttpStatus.NO_CONTENT)
	public Response deleteUser(@PathVariable("id") long id) {
		service.deleteUser(id);
		return new ResponseBuilder(Status.SUCCESS).build();
	}
}
