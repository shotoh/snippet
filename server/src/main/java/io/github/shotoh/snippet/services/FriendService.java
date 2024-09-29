package io.github.shotoh.snippet.services;

import io.github.shotoh.snippet.exceptions.ResourceNotFoundException;
import io.github.shotoh.snippet.mappers.FriendMapper;
import io.github.shotoh.snippet.models.friends.Friend;
import io.github.shotoh.snippet.models.friends.FriendDTO;
import io.github.shotoh.snippet.repositories.FriendRepository;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

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
