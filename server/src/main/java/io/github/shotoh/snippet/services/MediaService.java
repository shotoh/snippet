package io.github.shotoh.snippet.services;

import io.github.shotoh.snippet.exceptions.ResourceAlreadyExistsException;
import io.github.shotoh.snippet.exceptions.ResourceNotFoundException;
import io.github.shotoh.snippet.mappers.MediaMapper;
import io.github.shotoh.snippet.models.medias.Media;
import io.github.shotoh.snippet.models.medias.MediaCreateDTO;
import io.github.shotoh.snippet.models.medias.MediaDTO;
import io.github.shotoh.snippet.repositories.MediaRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

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
