package to.us.snippet.services;

import to.us.snippet.exceptions.ResourceAlreadyExistsException;
import to.us.snippet.exceptions.ResourceNotFoundException;
import to.us.snippet.mappers.MediaMapper;
import to.us.snippet.models.medias.Media;
import to.us.snippet.models.medias.MediaCreateDTO;
import to.us.snippet.models.medias.MediaDTO;
import to.us.snippet.repositories.MediaRepository;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class MediaService {
	private final MediaRepository repository;
	private final MediaMapper mapper;

	@Autowired
	public MediaService(MediaRepository repository, MediaMapper mapper) {
		this.repository = repository;
		this.mapper = mapper;
	}

	public Media getMedia(long id) {
		return repository.findById(id).orElseThrow(() -> new ResourceNotFoundException("id", "Media not found with this id"));
	}

	public List<MediaDTO> retrieveMedias() {
		return repository.findAll().stream().map(mapper::toDTO).toList();
	}

	public MediaDTO createMedia(MediaCreateDTO mediaCreateDTO) {
		if (repository.existsById(mediaCreateDTO.getId())) {
			throw new ResourceAlreadyExistsException("id", "Media already exists with this id");
		}
		Media media = repository.save(mapper.toEntity(mediaCreateDTO));
		return mapper.toDTO(media);
	}

	public MediaDTO retrieveMedia(long id) {
		Media media = getMedia(id);
		return mapper.toDTO(media);
	}

	public MediaDTO updateMedia(long id, MediaDTO mediaDTO) {
		Media media = getMedia(id);
		mapper.updateEntity(mediaDTO, media);
		repository.save(media);
		return mapper.toDTO(media);
	}

	public void deleteMedia(long id) {
		repository.deleteById(id);
	}
}
