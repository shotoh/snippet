package to.us.snippet.commentlikes;

import to.us.snippet.commentlikes.CommentLike;
import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface CommentLikeRepository extends JpaRepository<CommentLike, Long> {
	List<CommentLike> findAllByCommentId(long commentId);

	CommentLike findCommentLikeByUserIdAndCommentId(long userId, long commentId);

	boolean existsByUserIdAndCommentId(long userId, long commentId);
}
