package az.msblog.controller;

import az.msblog.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("api/users")
public class UserController {

    private final UserService userService;

    @Autowired
    public UserController(UserService userService) {
        this.userService = userService;
    }

    @PostMapping("{userId}/follow")
    public void followUser(@PathVariable Long userId) {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        String followerName = authentication.getName();
        userService.followUser(userId, followerName);
    }

    @DeleteMapping("{userId}/unfollow")
    public void unfollowUser(@PathVariable Long userId) {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        String followerName = authentication.getName();
        userService.unfollowUser(userId, followerName);
    }

}
