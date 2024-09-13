package io.github.shotoh.uzi.models.likes;

import io.github.shotoh.uzi.models.posts.PostDTO;
import io.github.shotoh.uzi.models.users.UserDTO;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter @Setter @NoArgsConstructor
public class LikeDTO {
    private long id;

    private UserDTO user;

    private PostDTO post;
}
