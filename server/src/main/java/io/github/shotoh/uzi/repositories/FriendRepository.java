package io.github.shotoh.uzi.repositories;

import io.github.shotoh.uzi.models.friends.Friend;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface FriendRepository extends JpaRepository<Friend, Long> {
}
