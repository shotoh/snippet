package to.us.snippet.messages;

import to.us.snippet.exceptions.ResourceNotFoundException;
import to.us.snippet.friends.FriendService;
import to.us.snippet.friends.Friend;
import to.us.snippet.friends.FriendStatus;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import to.us.snippet.security.AuthService;

@Service
public class MessageService {
	private final MessageRepository repository;
	private final MessageMapper mapper;

	private final AuthService authService;
	private final FriendService friendService;

	@Autowired
	public MessageService(MessageRepository repository, MessageMapper mapper, AuthService authService, FriendService friendService) {
		this.repository = repository;
		this.mapper = mapper;
		this.authService = authService;
		this.friendService = friendService;
	}

	public Message getMessage(long id) {
		return repository.findById(id).orElseThrow(() -> new ResourceNotFoundException("id", "Message not found with this id"));
	}

	public List<MessageDTO> retrieveMessagesByFromAndTo(long fromId, long toId) {
		authService.check(fromId);
		List<Message> messages = new ArrayList<>();
		messages.addAll(repository.findAllByFromIdAndToId(fromId, toId));
		messages.addAll(repository.findAllByFromIdAndToId(toId, fromId));
		return messages.stream()
				.sorted(Comparator.comparingLong(Message::getTimestamp))
				.map(mapper::toDTO)
				.toList();
	}

	public MessageDTO createMessage(MessageCreateDTO messageCreateDTO) {
		messageCreateDTO.setFromId(authService.userId());
		Friend friend = friendService.getFriendByFromAndTo(messageCreateDTO.getFromId(), messageCreateDTO.getToId());
		if (friend == null || friend.getStatus() != FriendStatus.FRIEND) throw new ResourceNotFoundException("from", "These users are not friends");
		Message message = repository.save(mapper.toEntity(messageCreateDTO));
		return mapper.toDTO(message);
	}

	public MessageDTO updateMessage(long id, MessageDTO messageDTO) {
		Message message = getMessage(id);
		authService.check(message);
		mapper.updateEntity(messageDTO, message);
		repository.save(message);
		return mapper.toDTO(message);
	}

	public void deleteMessage(long id) {
		Message message = getMessage(id);
		authService.check(message);
		repository.deleteById(message.getId());
	}
}
