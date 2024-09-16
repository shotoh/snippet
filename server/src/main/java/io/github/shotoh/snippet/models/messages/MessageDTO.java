package io.github.shotoh.snippet.models.messages;

import io.github.shotoh.snippet.models.users.UserDTO;
import jakarta.validation.constraints.Size;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter @Setter @NoArgsConstructor
public class MessageDTO {
    private long id;

    private UserDTO from;

    private UserDTO to;

    @Size(min = 1, max = 1023)
    private String content;
}
