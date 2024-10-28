package to.us.snippet.posts;

import to.us.snippet.posts.Post;
import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface PostRepository extends JpaRepository<Post, Long> {
	List<Post> findAllByUserId(long userId);
}
