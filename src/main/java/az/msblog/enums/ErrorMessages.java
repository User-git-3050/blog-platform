package az.msblog.enums;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public enum ErrorMessages {
    INVALID_USER_REQUEST("Invalid user request"),
    USER_ALREADY_EXISTS("User already exists"),
    USER_NOT_FOUND("User not found with username: %s"),
    USER_NOT_FOUND_WITH_ID("User not found with id : %s"),
    POST_NOT_FOUND("Post not found with id: %s"),
    COMMENT_NOT_FOUND("Comment not found with id: %s"),
    CATEGORY_NOT_FOUND("Category not found with id: %s"),
    CATEGORY_NOT_FOUND_WITH_NAME("Category not found with name : %s"),
    TAG_NOT_FOUND("Tag not found with id : %s"),
    TAG_NOT_FOUND_WITH_NAME("Tag not found with name : %s"),
    USER_ALREADY_LIKED("This post is already liked"),
    LIKE_ALREADY_DELETED("This user has already deleted like"),
    TOKEN_IS_EXPIRED("Jwt token has expired"),
    TOKEN_IS_INVALID("Jwt token is invalid"),
    USER_ALREADY_FOLLOWED("This user is already followed"),
    USER_CANNOT_FOLLOW("User cannot follow himself"),
    USER_CANNOT_UNFOLLOW("User cannot unfollow himself"),
    USER_NOT_FOLLOWING("User is not following");


    private final String message;
}
