package io.github.shotoh.uzi.repositories;

import io.github.shotoh.uzi.models.likes.Like;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface LikeRepository extends JpaRepository<Like, Long> {
    boolean existsByUserIdAndPostId(long userId, long postId);
}
