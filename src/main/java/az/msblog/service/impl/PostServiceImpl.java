package az.msblog.service.impl;

import az.msblog.dao.request.CommentRequest;
import az.msblog.dao.response.CommentResponse;
import az.msblog.dao.request.PostRequest;
import az.msblog.dao.response.PostResponse;
import az.msblog.entity.*;
import az.msblog.exceptions.ActionAlreadyDoneException;
import az.msblog.exceptions.NotFoundException;
import az.msblog.repository.*;
import az.msblog.service.PostService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

import static az.msblog.enums.ErrorMessages.*;
import static az.msblog.mapper.CommentMapper.*;
import static az.msblog.mapper.PostMapper.*;
import static java.lang.String.format;

@Service
public class PostServiceImpl implements PostService {
    private final PostRepository postRepository;
    private final UserRepository userRepository;
    private final CommentRepository commentRepository;
    private final CategoryRepository categoryRepository;
    private final TagRepository tagRepository;

    @Autowired
    public PostServiceImpl(PostRepository postRepository, CommentRepository commentRepository, UserRepository userRepository, CategoryRepository categoryRepository, TagRepository tagRepository) {
        this.postRepository = postRepository;
        this.userRepository = userRepository;
        this.commentRepository = commentRepository;
        this.categoryRepository = categoryRepository;
        this.tagRepository = tagRepository;
    }

    @Override
    public List<PostResponse> findAllBlogs() {
        return postRepository.findAll().stream().map(POST_MAPPER::mapToResponse).toList();
    }

    @Override
    public PostResponse findBlogById(Long id) {
        return postRepository.findById(id)
                .map(POST_MAPPER::mapToResponse)
                .orElseThrow(() -> new NotFoundException(
                        format(
                                POST_NOT_FOUND.getMessage(),
                                id
                        )
                ));
    }

    @Override
    public void createBlog(PostRequest postRequest) {
        List<CategoryEntity> categories = mapToCategoryList(postRequest);
        List<TagEntity> tags = mapToTagList(postRequest);

        postRepository.save(POST_MAPPER.mapToEntity(postRequest, categories, tags));
    }

    @Override
    public void updateBlog(Long id, PostRequest postRequest) {
        PostEntity postEntity = postRepository.findById(id).
                orElseThrow(() -> new NotFoundException(
                        format(
                                POST_NOT_FOUND.getMessage(),
                                id
                        )
                ));
        postEntity.setName(postRequest.getName());
        postEntity.setDescription(postRequest.getDescription());

        if (postRequest.getPostCategories() != null) {
            List<CategoryEntity> categories = mapToCategoryList(postRequest);
            postEntity.setPostCategories(categories);
        }

        if (postRequest.getTags() != null) {
            List<TagEntity> tags = mapToTagList(postRequest);
            postEntity.setTags(tags);
        }
        postRepository.save(postEntity);
    }

    private List<TagEntity> mapToTagList(PostRequest postRequest) {
        return new ArrayList<>(postRequest.getTags().stream()
                .map(tag -> {
                    TagEntity tagEntity = tagRepository.findByName(tag);
                    if (tagEntity == null) {
                        throw new NotFoundException(
                                format(
                                        TAG_NOT_FOUND_WITH_NAME.getMessage(),
                                        tag
                                )
                        );
                    }
                    return tagEntity;
                })
                .toList()
        );
    }

    private List<CategoryEntity> mapToCategoryList(PostRequest postRequest) {
        return new ArrayList<>(postRequest.getPostCategories().stream()
                .map(category -> {
                    CategoryEntity categoryEntity = categoryRepository.findByName(category);
                    if (categoryEntity == null) {
                        throw new NotFoundException(
                                format(
                                        CATEGORY_NOT_FOUND_WITH_NAME.getMessage(),
                                        category
                                )
                        );
                    }
                    return categoryEntity;
                })
                .toList());
    }

    @Override
    public void deleteBlog(Long id) {
        PostEntity postEntity = postRepository.findById(id).
                orElseThrow(() -> new NotFoundException(
                        format(
                                POST_NOT_FOUND.getMessage(),
                                id
                        )
                ));
        postRepository.delete(postEntity);
    }

    @Override
    public void addComment(Long id, CommentRequest commentRequest, String username) {
        PostEntity postEntity = postRepository.findById(id).
                orElseThrow(() -> new NotFoundException(
                        format(
                                POST_NOT_FOUND.getMessage(),
                                id
                        )
                ));
        UserEntity userEntity = userRepository.findByUsername(username).
                orElseThrow(() -> new UsernameNotFoundException(
                        format(
                                USER_NOT_FOUND.getMessage(),
                                username
                        )
                ));
        CommentsEntity commentsEntity = COMMENT_MAPPER.mapToCommentsEntity(postEntity, commentRequest, userEntity);
        postEntity.getComments().add(commentsEntity);
        postEntity.setCommentCount(postEntity.getCommentCount() + 1);
        commentRepository.save(commentsEntity);
    }

    @Override
    public List<CommentResponse> getAllComments(Long id) {
        PostEntity postEntity = postRepository.findById(id)
                .orElseThrow(() -> new NotFoundException(
                        format(
                                POST_NOT_FOUND.getMessage(),
                                id
                        )
                ));
        return postEntity.getComments().stream().map(COMMENT_MAPPER::mapToCommentResponse).toList();


    }

    @Override
    public void likePost(Long id, String username) {
        PostEntity postEntity = postRepository.findById(id)
                .orElseThrow(() -> new NotFoundException(
                        format(
                                POST_NOT_FOUND.getMessage(),
                                id
                        )
                ));
        UserEntity userEntity = userRepository.findByUsername(username).
                orElseThrow(() -> new UsernameNotFoundException(
                        format(
                                USER_NOT_FOUND.getMessage(),
                                username
                        )
                ));
        if (!postEntity.getLikedUsers().contains(userEntity)) {
            postEntity.getLikedUsers().add(userEntity);
            postEntity.setLikeCount(postEntity.getLikeCount() + 1);
            postRepository.save(postEntity);
        } else {
            throw new ActionAlreadyDoneException(USER_ALREADY_LIKED.getMessage());
        }

    }

    @Override
    public void deleteLike(Long id, String username) {
        PostEntity postEntity = postRepository.findById(id)
                .orElseThrow(() -> new NotFoundException(
                        format(
                                POST_NOT_FOUND.getMessage(),
                                id
                        )
                ));
        UserEntity userEntity = userRepository.findByUsername(username).
                orElseThrow(() -> new UsernameNotFoundException(
                        format(
                                USER_NOT_FOUND.getMessage(),
                                username
                        )
                ));
        if (postEntity.getLikedUsers().contains(userEntity)) {
            postEntity.getLikedUsers().remove(userEntity);
            postEntity.setLikeCount(postEntity.getLikeCount() - 1);
            postRepository.save(postEntity);
        } else {
            throw new ActionAlreadyDoneException(LIKE_ALREADY_DELETED.getMessage());
        }
    }

    @Override
    public void updateComment(Long commentId, CommentRequest commentRequest, String username) {
        CommentsEntity commentsEntity = commentRepository.findById(commentId)
                .orElseThrow(() -> new NotFoundException(
                        format(
                                COMMENT_NOT_FOUND.getMessage(),
                                commentId
                        )
                ));
        UserEntity userEntity = userRepository.findByUsername(username).
                orElseThrow(() -> new UsernameNotFoundException(
                        format(
                                USER_NOT_FOUND.getMessage(),
                                username
                        )
                ));
        if (userEntity.getComments().contains(commentsEntity)) {
            commentsEntity.setDescription(commentRequest.getDescription());
            commentsEntity.setModified(LocalDateTime.now());
            commentRepository.save(commentsEntity);
        }
    }

}
