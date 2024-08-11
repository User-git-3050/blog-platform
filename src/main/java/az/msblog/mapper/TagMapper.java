package az.msblog.mapper;

import az.msblog.dao.request.TagRequest;
import az.msblog.dao.response.TagResponse;
import az.msblog.entity.TagEntity;

import java.time.LocalDateTime;
import java.util.List;

public enum TagMapper {
    TAG_MAPPER;

    public TagEntity mapToEntity(TagRequest tagRequest) {
        return TagEntity.builder()
                .name(tagRequest.getName())
                .created(LocalDateTime.now())
                .description(tagRequest.getDescription())
                .build();
    }

    public TagResponse mapToResponse(TagEntity tagEntity) {
        return TagResponse.builder()
                .id(tagEntity.getId())
                .name(tagEntity.getName())
                .description(tagEntity.getDescription())
                .created(tagEntity.getCreated())
                .modified(tagEntity.getModified())
                .build();
    }


}
