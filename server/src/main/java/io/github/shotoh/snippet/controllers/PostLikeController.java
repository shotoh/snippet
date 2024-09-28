package io.github.shotoh.snippet.controllers;

import io.github.shotoh.snippet.models.postlikes.PostLikeCreateDTO;
import io.github.shotoh.snippet.models.postlikes.PostLikeDTO;
import io.github.shotoh.snippet.responses.Success;
import io.github.shotoh.snippet.services.PostLikeService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping(path = "/api/post-likes")
@PreAuthorize("hasRole('ADMIN')")
public class PostLikeController {
    private final PostLikeService service;

    @Autowired
    public PostLikeController(PostLikeService service) {
        this.service = service;
    }

    @GetMapping
    @ResponseStatus(HttpStatus.OK)
    public Success<List<PostLikeDTO>> retrievePostLikes() {
        return new Success<>(service.retrievePostLikes());
    }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public Success<PostLikeDTO> createPostLike(@RequestBody @Valid PostLikeCreateDTO postLikeCreateDTO) {
        return new Success<>(service.createPostLike(postLikeCreateDTO));
    }

    @GetMapping("/{id}")
    @ResponseStatus(HttpStatus.OK)
    public Success<PostLikeDTO> retrievePostLike(@PathVariable("id") long id) {
        return new Success<>(service.retrievePostLike(id));
    }

    @DeleteMapping("/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public Success<Void> deletePostLike(@PathVariable("id") long id) {
        service.deletePostLike(id);
        return new Success<>();
    }
}
