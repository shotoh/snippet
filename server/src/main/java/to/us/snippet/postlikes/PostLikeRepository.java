package to.us.snippet.postlikes;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface PostLikeRepository extends JpaRepository<PostLike, Long> {
	void deletePostLikeByUserIdAndPostId(long userId, long postId);
}
