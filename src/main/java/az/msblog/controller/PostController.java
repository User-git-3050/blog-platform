package az.msblog.controller;

import az.msblog.dao.request.CommentRequest;
import az.msblog.dao.response.CommentResponse;
import az.msblog.dao.request.PostRequest;
import az.msblog.dao.response.PostResponse;
import az.msblog.service.PostService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("api/posts")
public class PostController {
    private final PostService postService;

    @Autowired
    public PostController(PostService postService) {
        this.postService = postService;
    }


    @GetMapping()
    public ResponseEntity<List<PostResponse>> getAllBlogs() {
        return ResponseEntity.ok(postService.findAllBlogs());
    }

    @GetMapping("{id}")
    public ResponseEntity<PostResponse> getBlogById(@PathVariable Long id) {
        return ResponseEntity.ok(postService.findBlogById(id));
    }

    @PostMapping()
    public void createBlog(@RequestBody PostRequest postRequest) {
        postService.createBlog(postRequest);
    }

    @PutMapping("{id}")
    public void updateBlog(@PathVariable Long id, @RequestBody PostRequest postRequest) {
        postService.updateBlog(id, postRequest);
    }

    @DeleteMapping("{id}")
    public void deleteBlog(@PathVariable Long id) {
        postService.deleteBlog(id);
    }

    @GetMapping("{postId}/comments")
    public ResponseEntity<List<CommentResponse>> getAllComments(@PathVariable Long postId) {
        return ResponseEntity.ok(postService.getAllComments(postId));
    }

    @PostMapping("{postId}/comments")
    public void addComment(@PathVariable Long postId, @RequestBody CommentRequest commentRequest) {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        String username = authentication.getName();
        postService.addComment(postId, commentRequest, username);
    }

    @PutMapping("{commentId}/comments")
    public void updateComment(@PathVariable Long commentId, @RequestBody CommentRequest commentRequest) {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        String username = authentication.getName();
        postService.updateComment(commentId, commentRequest, username);
    }

    @PostMapping("{postId}/likes")
    public void addLike(@PathVariable Long postId) {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        String username = authentication.getName();
        postService.likePost(postId, username);
    }

    @DeleteMapping("{postId}/likes")
    public void deleteLike(@PathVariable Long postId) {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        String username = authentication.getName();
        postService.deleteLike(postId, username);
    }


}
