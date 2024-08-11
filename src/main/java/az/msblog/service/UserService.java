package az.msblog.service;

import az.msblog.dao.request.UserRequest;
import az.msblog.entity.UserEntity;
import org.springframework.stereotype.Service;

@Service
public interface UserService {
    UserEntity findUserByEmail(String email);

    void saveUser(UserRequest userRequest);

    void followUser(Long userId, String followerName);

    void unfollowUser(Long userId, String followerName);
}
