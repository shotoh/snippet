package io.github.shotoh.uzi.models.comments;

import io.github.shotoh.uzi.models.posts.Post;
import io.github.shotoh.uzi.models.users.User;
import jakarta.validation.constraints.Size;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter @Setter @NoArgsConstructor
public class CommentDTO {
    private long id;

    private User user;

    private Post post;

    @Size(min = 1, max = 1023)
    private String content;
}
