package io.github.shotoh.uzi.controllers;

import io.github.shotoh.uzi.models.comments.CommentCreateDTO;
import io.github.shotoh.uzi.models.comments.CommentDTO;
import io.github.shotoh.uzi.responses.Success;
import io.github.shotoh.uzi.services.CommentService;
import jakarta.validation.Valid;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping(path = "/api/comments")
public class CommentController {
    private final CommentService service;

    @Autowired
    public CommentController(CommentService service) {
        this.service = service;
    }

    @GetMapping
    @ResponseStatus(HttpStatus.OK)
    public Success<List<CommentDTO>> retrieveComments() {
        return new Success<>(service.retrieveComments());
    }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public Success<CommentDTO> createComment(@RequestBody @Valid CommentCreateDTO commentCreateDTO) {
        return new Success<>(service.createComment(commentCreateDTO));
    }

    @GetMapping("/{id}")
    @ResponseStatus(HttpStatus.OK)
    public Success<CommentDTO> retrieveComment(@PathVariable("id") long id) {
        return new Success<>(service.retrieveComment(id));
    }

    @PatchMapping("/{id}")
    @ResponseStatus(HttpStatus.OK)
    public Success<CommentDTO> updateComment(@PathVariable("id") long id, @RequestBody @Valid CommentDTO commentDTO) {
        return new Success<>(service.updateComment(id, commentDTO));
    }

    @DeleteMapping("/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public Success<Void> deleteComment(@PathVariable("id") long id) {
        service.deleteComment(id);
        return new Success<>();
    }
}
