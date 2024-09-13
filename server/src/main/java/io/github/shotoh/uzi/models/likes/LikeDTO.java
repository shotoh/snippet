package io.github.shotoh.uzi.models.likes;

import io.github.shotoh.uzi.models.posts.Post;
import io.github.shotoh.uzi.models.users.User;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter @Setter @NoArgsConstructor
public class LikeDTO {
    private long id;

    private User user;

    private Post post;
}