package to.us.snippet.friends;

import to.us.snippet.friends.Friend;
import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface FriendRepository extends JpaRepository<Friend, Long> {
	List<Friend> findAllByFromId(long fromId);

	List<Friend> findAllByToId(long toId);

	Friend findFriendByFromIdAndToId(long fromId, long toId);

	boolean existsByFromIdAndToId(long fromId, long toId);
}
