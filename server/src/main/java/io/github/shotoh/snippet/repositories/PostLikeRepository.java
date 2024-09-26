package io.github.shotoh.snippet.repositories;

import io.github.shotoh.snippet.models.postlikes.PostLike;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface PostLikeRepository extends JpaRepository<PostLike, Long> {
    boolean existsByUserIdAndPostId(long userId, long postId);
}
