package io.github.shotoh.snippet.repositories;

import io.github.shotoh.snippet.models.likes.Like;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface LikeRepository extends JpaRepository<Like, Long> {
    boolean existsByUserIdAndPostId(long userId, long postId);
}
