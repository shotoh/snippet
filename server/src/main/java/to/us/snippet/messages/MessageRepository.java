package to.us.snippet.messages;

import to.us.snippet.messages.Message;
import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface MessageRepository extends JpaRepository<Message, Long> {
	List<Message> findAllByFromIdAndToId(long fromId, long toId);
}
