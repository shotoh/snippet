package io.github.shotoh.snippet.services;

import io.github.shotoh.snippet.exceptions.ResourceAlreadyExistsException;
import io.github.shotoh.snippet.exceptions.ResourceNotFoundException;
import io.github.shotoh.snippet.mappers.FriendMapper;
import io.github.shotoh.snippet.models.friends.Friend;
import io.github.shotoh.snippet.models.friends.FriendCreateDTO;
import io.github.shotoh.snippet.models.friends.FriendDTO;
import io.github.shotoh.snippet.models.friends.FriendStatus;
import io.github.shotoh.snippet.repositories.FriendRepository;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class FriendService {
	private final FriendRepository repository;
	private final FriendMapper mapper;

	private final AuthService authService;

	@Autowired
	public FriendService(FriendRepository repository, FriendMapper mapper, AuthService authService) {
		this.repository = repository;
		this.mapper = mapper;
		this.authService = authService;
	}

	public Friend getFriend(long id) {
		return repository.findById(id).orElseThrow(() -> new ResourceNotFoundException("id", "Friend not found with this id"));
	}

	public Friend getFriendByFromAndTo(long fromId, long toId) {
		return repository.findFriendByFromIdAndToId(fromId, toId);
	}

	public List<FriendDTO> retrieveFriendsByFrom(long fromId) {
		return repository.findAllByFromId(fromId).stream().map(mapper::toDTO).toList();
	}
	
	public List<FriendDTO> retrieveFriendsByTo(long toId) {
		return repository.findAllByToId(toId).stream().map(mapper::toDTO).toList();
	}

	public FriendDTO retrieveFriendByFromAndTo(long fromId, long toId) {
		Friend friend = getFriendByFromAndTo(fromId, toId);
		if (friend == null) throw new ResourceNotFoundException("from", "Friend not found with these users");
		authService.check(friend);
		return mapper.toDTO(friend);
	}

	public FriendDTO createFriend(FriendCreateDTO friendCreateDTO) {
		friendCreateDTO.setFromId(authService.userId());
		long fromId = friendCreateDTO.getFromId();
		long toId = friendCreateDTO.getToId();
		if (repository.existsByFromIdAndToId(friendCreateDTO.getFromId(), friendCreateDTO.getToId())) {
			throw new ResourceAlreadyExistsException("fromId", "Friend already exists with this user");
		}
		Friend friend = mapper.toEntity(friendCreateDTO);
		if (repository.existsByFromIdAndToId(toId, fromId)) {
			Friend otherFriend = repository.findFriendByFromIdAndToId(toId, fromId);
			if (otherFriend.getStatus() == FriendStatus.PENDING) {
				friend.setStatus(FriendStatus.FRIEND);
				otherFriend.setStatus(FriendStatus.FRIEND);
				repository.save(otherFriend);
			}
		}
		return mapper.toDTO(repository.save(friend));
	}

	public void deleteFriend(long id) {
		Friend friend = getFriend(id);
		authService.check(friend);
		repository.deleteById(friend.getId());
		long fromId = friend.getFrom().getId();
		long toId = friend.getTo().getId();
		if (repository.existsByFromIdAndToId(toId, fromId)) {
			Friend otherFriend = repository.findFriendByFromIdAndToId(toId, fromId);
			repository.deleteById(otherFriend.getId());
		}
	}
}
