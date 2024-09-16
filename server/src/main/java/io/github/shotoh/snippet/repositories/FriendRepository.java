package io.github.shotoh.snippet.repositories;

import io.github.shotoh.snippet.models.friends.Friend;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface FriendRepository extends JpaRepository<Friend, Long> {
}
