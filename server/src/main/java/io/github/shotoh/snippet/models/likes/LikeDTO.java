package io.github.shotoh.snippet.models.likes;

import io.github.shotoh.snippet.models.posts.PostDTO;
import io.github.shotoh.snippet.models.users.UserDTO;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter @Setter @NoArgsConstructor
public class LikeDTO {
    private long id;

    private UserDTO user;

    private PostDTO post;
}
