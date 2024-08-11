package az.msblog.dao.response;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;

@Data
@AllArgsConstructor
@Builder
public class PostResponse {
    private Long id;
    private String name;
    private String description;
}
