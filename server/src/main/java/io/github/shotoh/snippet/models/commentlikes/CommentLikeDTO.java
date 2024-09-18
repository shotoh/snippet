package io.github.shotoh.snippet.models.commentlikes;

import io.github.shotoh.snippet.models.comments.CommentDTO;
import io.github.shotoh.snippet.models.users.UserDTO;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter @Setter @NoArgsConstructor
public class CommentLikeDTO {
    private long id;

    private UserDTO user;

    private CommentDTO comment;
}
