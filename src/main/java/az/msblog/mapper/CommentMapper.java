package az.msblog.mapper;

import az.msblog.dao.request.CommentRequest;
import az.msblog.dao.response.CommentResponse;
import az.msblog.entity.CommentsEntity;
import az.msblog.entity.PostEntity;
import az.msblog.entity.UserEntity;

import java.time.LocalDateTime;

public enum CommentMapper {
    COMMENT_MAPPER;

    public CommentsEntity mapToCommentsEntity(PostEntity postEntity, CommentRequest commentRequest, UserEntity userEntity) {
        return CommentsEntity.builder()
                .description(commentRequest.getDescription())
                .created(LocalDateTime.now())
                .user(userEntity)
                .post(postEntity)
                .build();

    }

    public CommentResponse mapToCommentResponse(CommentsEntity commentsEntity) {
        return CommentResponse.builder().
                id(commentsEntity.getId()).
                description(commentsEntity.getDescription()).
                created(commentsEntity.getCreated()).
                modified(commentsEntity.getModified())
                .build();
    }
}
