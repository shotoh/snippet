package io.github.shotoh.uzi.controllers;

import io.github.shotoh.uzi.models.medias.MediaCreateDTO;
import io.github.shotoh.uzi.models.medias.MediaDTO;
import io.github.shotoh.uzi.responses.Success;
import io.github.shotoh.uzi.services.MediaService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping(path = "/api/media")
public class MediaController {
    private final MediaService service;

    @Autowired
    public MediaController(MediaService service) {
        this.service = service;
    }

    @GetMapping
    @ResponseStatus(HttpStatus.OK)
    public Success<List<MediaDTO>> retrieveMedias() {
        return new Success<>(service.retrieveMedias());
    }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public Success<MediaDTO> createMedia(@RequestBody @Valid MediaCreateDTO mediaCreateDTO) {
        return new Success<>(service.createMedia(mediaCreateDTO));
    }

    @GetMapping("/{id}")
    @ResponseStatus(HttpStatus.OK)
    public Success<MediaDTO> retrieveMedia(@PathVariable("id") long id) {
        return new Success<>(service.retrieveMedia(id));
    }

    @PatchMapping("/{id}")
    @ResponseStatus(HttpStatus.OK)
    public Success<MediaDTO> updateMedia(@PathVariable("id") long id, @RequestBody @Valid MediaDTO mediaDTO) {
        return new Success<>(service.updateMedia(id, mediaDTO));
    }

    @DeleteMapping("/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public Success<Void> deleteMedia(@PathVariable("id") long id) {
        service.deleteMedia(id);
        return new Success<>();
    }
}
