package to.us.snippet.commentlikes;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface CommentLikeRepository extends JpaRepository<CommentLike, Long> {
	void deleteCommentLikeByUserIdAndCommentId(long userId, long commentId);
}
