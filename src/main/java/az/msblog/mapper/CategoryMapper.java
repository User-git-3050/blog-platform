package az.msblog.mapper;

import az.msblog.dao.request.CategoryRequest;
import az.msblog.dao.response.CategoryResponse;
import az.msblog.entity.CategoryEntity;

import java.time.LocalDateTime;

public enum CategoryMapper {
    CATEGORY_MAPPER;

    public CategoryEntity mapToCategory(CategoryRequest categoryRequest) {
        return CategoryEntity.builder()
                .name(categoryRequest.getName())
                .created(LocalDateTime.now())
                .description(categoryRequest.getDescription())
                .build();
    }

    public CategoryResponse mapToResponse(CategoryEntity categoryEntity) {
        return CategoryResponse.builder()
                .id(categoryEntity.getId())
                .name(categoryEntity.getName())
                .description(categoryEntity.getDescription())
                .created(categoryEntity.getCreated())
                .modified(categoryEntity.getModified())
                .build();
    }
}
