package io.github.shotoh.uzi.services;

import io.github.shotoh.uzi.exceptions.ResourceNotFoundException;
import io.github.shotoh.uzi.mappers.FriendMapper;
import io.github.shotoh.uzi.models.friends.Friend;
import io.github.shotoh.uzi.models.friends.FriendDTO;
import io.github.shotoh.uzi.repositories.FriendRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class FriendService {
    private final FriendRepository repository;
    private final FriendMapper mapper;

    @Autowired
    public FriendService(FriendRepository repository, FriendMapper mapper) {
        this.repository = repository;
        this.mapper = mapper;
    }

    public Friend getFriend(long id) {
        return repository.findById(id).orElseThrow(() -> new ResourceNotFoundException("id", "Friend not found with this id"));
    }

    public List<FriendDTO> retrieveFriends() {
        return repository.findAll().stream().map(mapper::toDTO).toList();
    }

    public FriendDTO retrieveFriend(long id) {
        Friend friend = getFriend(id);
        return mapper.toDTO(friend);
    }
}
