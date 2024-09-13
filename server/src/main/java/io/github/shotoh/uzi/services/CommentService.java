package io.github.shotoh.uzi.services;

import io.github.shotoh.uzi.exceptions.ResourceAlreadyExistsException;
import io.github.shotoh.uzi.exceptions.ResourceNotFoundException;
import io.github.shotoh.uzi.mappers.CommentMapper;
import io.github.shotoh.uzi.models.comments.Comment;
import io.github.shotoh.uzi.models.comments.CommentCreateDTO;
import io.github.shotoh.uzi.models.comments.CommentDTO;
import io.github.shotoh.uzi.repositories.CommentRepository;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class CommentService {
    private final CommentRepository repository;
    private final CommentMapper mapper;

    @Autowired
    public CommentService(CommentRepository repository, CommentMapper mapper) {
        this.repository = repository;
        this.mapper = mapper;
    }

    public Comment getComment(long id) {
        return repository.findById(id).orElseThrow(() -> new ResourceNotFoundException("id", "Comment not found with this id"));
    }

    public List<CommentDTO> retrieveComments() {
        return repository.findAll().stream().map(mapper::toDTO).toList();
    }

    public CommentDTO createComment(CommentCreateDTO commentCreateDTO) {
        if (repository.existsById(commentCreateDTO.getId())) {
            throw new ResourceAlreadyExistsException("id", "Comment already exists with this id");
        }
        Comment comment = repository.save(mapper.toEntity(commentCreateDTO));
        return mapper.toDTO(comment);
    }

    public CommentDTO updateComment(long id, CommentDTO commentDTO) {
        Comment comment = getComment(id);
        mapper.updateEntity(commentDTO, comment);
        repository.save(comment);
        return mapper.toDTO(comment);
    }

    public CommentDTO retrieveComment(long id) {
        Comment comment = getComment(id);
        return mapper.toDTO(comment);
    }

    public void deleteComment(long id) {
        repository.deleteById(id);
    }
}
