package io.github.shotoh.uzi.services;

import io.github.shotoh.uzi.exceptions.ResourceAlreadyExistsException;
import io.github.shotoh.uzi.exceptions.ResourceNotFoundException;
import io.github.shotoh.uzi.mappers.LikeMapper;
import io.github.shotoh.uzi.models.likes.Like;
import io.github.shotoh.uzi.models.likes.LikeCreateDTO;
import io.github.shotoh.uzi.models.likes.LikeDTO;
import io.github.shotoh.uzi.repositories.LikeRepository;
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

    public long getId(Like like) {
        return like.getId();
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
