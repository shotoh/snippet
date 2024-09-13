package io.github.shotoh.uzi.services;

import io.github.shotoh.uzi.exceptions.ResourceAlreadyExistsException;
import io.github.shotoh.uzi.exceptions.ResourceNotFoundException;
import io.github.shotoh.uzi.mappers.PostMapper;
import io.github.shotoh.uzi.models.posts.Post;
import io.github.shotoh.uzi.models.posts.PostDTO;
import io.github.shotoh.uzi.repositories.PostRepository;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class PostService {
    private final PostRepository repository;
    private final PostMapper mapper;

    @Autowired
    public PostService(PostRepository repository, PostMapper mapper) {
        this.repository = repository;
        this.mapper = mapper;
    }

    public long getId(Post post) {
        return post.getId();
    }

    public Post getPost(long id) {
        return repository.findById(id).orElseThrow(() -> new ResourceNotFoundException("id", "Post not found with this id"));
    }

    public List<PostDTO> retrievePosts() {
        return repository.findAll().stream().map(mapper::toDTO).toList();
    }

    public PostDTO createPost(PostDTO postDTO) {
        if (repository.existsById(postDTO.getId())) {
            throw new ResourceAlreadyExistsException("id", "Post already exists with this id");
        }
        Post post = repository.save(mapper.toEntity(postDTO));
        return mapper.toDTO(post);
    }

    public PostDTO retrievePost(long id) {
        Post post = getPost(id);
        return mapper.toDTO(post);
    }

    public PostDTO updatePost(long id, PostDTO postDTO) {
        Post post = getPost(id);
        mapper.updateEntity(postDTO, post);
        repository.save(post);
        return mapper.toDTO(post);
    }

    public void deletePost(long id) {
        repository.deleteById(id);
    }
}
