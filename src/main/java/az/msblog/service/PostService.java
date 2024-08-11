package az.msblog.service;


import az.msblog.dao.request.CommentRequest;
import az.msblog.dao.response.CommentResponse;
import az.msblog.dao.request.PostRequest;
import az.msblog.dao.response.PostResponse;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public interface PostService {
    List<PostResponse> findAllBlogs();

    PostResponse findBlogById(Long id);

    void createBlog(PostRequest postRequest);

    void updateBlog(Long id, PostRequest postRequest);

    void deleteBlog(Long id);

    void addComment(Long postId, CommentRequest commentRequest, String username);

    List<CommentResponse> getAllComments(Long postId);

    void likePost(Long id, String username);

    void deleteLike(Long id, String username);

    void updateComment(Long commentId, CommentRequest commentRequest, String username);
}
