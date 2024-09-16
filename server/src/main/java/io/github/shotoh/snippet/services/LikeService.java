package io.github.shotoh.snippet.services;

import io.github.shotoh.snippet.exceptions.ResourceAlreadyExistsException;
import io.github.shotoh.snippet.exceptions.ResourceNotFoundException;
import io.github.shotoh.snippet.mappers.LikeMapper;
import io.github.shotoh.snippet.models.likes.Like;
import io.github.shotoh.snippet.models.likes.LikeCreateDTO;
import io.github.shotoh.snippet.models.likes.LikeDTO;
import io.github.shotoh.snippet.repositories.LikeRepository;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class LikeService {
    private final LikeRepository repository;
    private final LikeMapper mapper;

    @Autowired
    public LikeService(LikeRepository repository, LikeMapper mapper) {
        this.repository = repository;
        this.mapper = mapper;
    }

    public Like getLike(long id) {
        return repository.findById(id).orElseThrow(() -> new ResourceNotFoundException("id", "Like not found with this id"));
    }

    public List<LikeDTO> retrieveLikes() {
        return repository.findAll().stream().map(mapper::toDTO).toList();
    }

    public LikeDTO createLike(LikeCreateDTO likeCreateDTO) {
        if (repository.existsById(likeCreateDTO.getId())) {
            throw new ResourceAlreadyExistsException("id", "Like already exists with this id");
        }
        if (repository.existsByUserIdAndPostId(likeCreateDTO.getUserId(), likeCreateDTO.getPostId())) {
            throw new ResourceAlreadyExistsException("userId", "Like already exists with this user and post id");
        }
        Like like = repository.save(mapper.toEntity(likeCreateDTO));
        return mapper.toDTO(like);
    }

    public LikeDTO retrieveLike(long id) {
        Like like = getLike(id);
        return mapper.toDTO(like);
    }

    public void deleteLike(long id) {
        repository.deleteById(id);
    }
}
