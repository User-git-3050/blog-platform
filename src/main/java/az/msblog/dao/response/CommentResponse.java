package az.msblog.dao.response;

import az.msblog.entity.PostEntity;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;

import java.time.LocalDateTime;

@Data
@AllArgsConstructor
@Builder
public class CommentResponse {
    private Long id;
    private String description;
    private LocalDateTime created;
    private LocalDateTime modified;
}
