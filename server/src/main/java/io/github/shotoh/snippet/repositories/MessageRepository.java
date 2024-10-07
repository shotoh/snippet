package io.github.shotoh.snippet.repositories;

import io.github.shotoh.snippet.models.messages.Message;
import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface MessageRepository extends JpaRepository<Message, Long> {
	List<Message> findAllByFromIdAndToId(long fromId, long toId);
}
