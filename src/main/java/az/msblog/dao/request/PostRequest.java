package az.msblog.dao.request;

import az.msblog.entity.CategoryEntity;
import az.msblog.entity.TagEntity;
import jakarta.validation.constraints.NotEmpty;
import lombok.AllArgsConstructor;
import lombok.Data;

import java.util.ArrayList;
import java.util.List;

@Data
@AllArgsConstructor
public class PostRequest {
    @NotEmpty(message = "you should give a name to post")
    private String name;
    private String description;
    private List<String> postCategories;
    private List<String> tags;
}
