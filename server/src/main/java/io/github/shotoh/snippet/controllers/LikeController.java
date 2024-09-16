package io.github.shotoh.snippet.controllers;

import io.github.shotoh.snippet.models.likes.LikeCreateDTO;
import io.github.shotoh.snippet.models.likes.LikeDTO;
import io.github.shotoh.snippet.responses.Success;
import io.github.shotoh.snippet.services.LikeService;
import jakarta.validation.Valid;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping(path = "/api/likes")
public class LikeController {
    private final LikeService service;

    @Autowired
    public LikeController(LikeService service) {
        this.service = service;
    }

    @GetMapping
    @ResponseStatus(HttpStatus.OK)
    public Success<List<LikeDTO>> retrieveLikes() {
        return new Success<>(service.retrieveLikes());
    }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public Success<LikeDTO> createLike(@RequestBody @Valid LikeCreateDTO likeCreateDTO) {
        return new Success<>(service.createLike(likeCreateDTO));
    }

    @GetMapping("/{id}")
    @ResponseStatus(HttpStatus.OK)
    public Success<LikeDTO> retrieveLike(@PathVariable("id") long id) {
        return new Success<>(service.retrieveLike(id));
    }

    @DeleteMapping("/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public Success<Void> deleteLike(@PathVariable("id") long id) {
        service.deleteLike(id);
        return new Success<>();
    }
}
