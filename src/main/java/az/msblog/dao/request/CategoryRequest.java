package az.msblog.dao.request;

import jakarta.validation.constraints.NotEmpty;
import lombok.Data;

@Data
public class CategoryRequest {
    @NotEmpty(message = "name should be written")
    private String name;
    private String description;
}
