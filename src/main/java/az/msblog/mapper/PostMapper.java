package az.msblog.mapper;

import az.msblog.dao.request.PostRequest;
import az.msblog.dao.response.PostResponse;
import az.msblog.entity.CategoryEntity;
import az.msblog.entity.PostEntity;
import az.msblog.entity.TagEntity;

import java.util.List;

public enum PostMapper {
    POST_MAPPER;

    public PostEntity mapToEntity(PostRequest postRequest, List<CategoryEntity> categoryEntityList, List<TagEntity> tagEntityList) {
        return PostEntity.builder()
                .name(postRequest.getName())
                .description(postRequest.getDescription())
                .postCategories(categoryEntityList)
                .tags(tagEntityList)
                .likeCount(0L)
                .commentCount(0L)
                .build();
    }

    public PostResponse mapToResponse(PostEntity postEntity) {
        return PostResponse.builder().
                id(postEntity.getId()).
                name(postEntity.getName()).
                description(postEntity.getDescription()).
                build();
    }

}
