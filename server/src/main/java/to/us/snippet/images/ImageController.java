package to.us.snippet.images;

import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
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
@RequestMapping(path = "/api/images")
@PreAuthorize("hasRole('USER')")
public class ImageController {
	private final ImageService service;

	@Autowired
	public ImageController(ImageService service) {
		this.service = service;
	}

	@GetMapping
	@ResponseStatus(HttpStatus.OK)
	public Response retrieveImages(@RequestParam(name = "post") long postId) {
		ResponseBuilder builder =  new ResponseBuilder(Status.SUCCESS);
		return builder.build();
	}

	@PostMapping
	@ResponseStatus(HttpStatus.CREATED)
	public Response createImage(@RequestBody @Valid ImageCreateDTO imageCreateDTO) {
		return new ResponseBuilder(Status.SUCCESS)
				.setData(service.createImage(imageCreateDTO))
				.build();
	}

	@GetMapping("/{id}")
	@ResponseStatus(HttpStatus.OK)
	public Response retrieveImage(@PathVariable("id") long id) {
		return new ResponseBuilder(Status.SUCCESS)
				.setData(service.retrieveImage(id))
				.build();
	}

	@DeleteMapping("/{id}")
	@ResponseStatus(HttpStatus.NO_CONTENT)
	public Response deleteImage(@PathVariable("id") long id) {
		service.deleteImage(id);
		return new ResponseBuilder(Status.SUCCESS).build();
	}
}
