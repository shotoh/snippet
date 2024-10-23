package to.us.snippet.friends;

import to.us.snippet.exceptions.ResourceAlreadyExistsException;
import to.us.snippet.exceptions.ResourceNotFoundException;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import to.us.snippet.auth.AuthService;

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
		long callerId = authService.userId();
		long fromId = friend.getFrom().getId();
		long toId = friend.getTo().getId();
		if (toId == callerId) {
			authService.check(toId);
		} else {
			authService.check(friend);
		}
		repository.deleteById(friend.getId());
		if (repository.existsByFromIdAndToId(toId, fromId)) {
			Friend otherFriend = repository.findFriendByFromIdAndToId(toId, fromId);
			if (otherFriend != null) {
				repository.deleteById(otherFriend.getId());
			}
		}
	}
}
