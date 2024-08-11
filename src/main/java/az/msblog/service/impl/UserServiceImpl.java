package az.msblog.service.impl;

import az.msblog.dao.request.UserRequest;
import az.msblog.entity.UserEntity;
import az.msblog.entity.UserRole;
import az.msblog.exceptions.ActionAlreadyDoneException;
import az.msblog.exceptions.SelfFollowException;
import az.msblog.exceptions.SelfUnfollowException;
import az.msblog.mapper.UserMapper;
import az.msblog.repository.RoleRepository;
import az.msblog.repository.UserRepository;
import az.msblog.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import static az.msblog.enums.ErrorMessages.*;
import static java.lang.String.format;

@Service
public class UserServiceImpl implements UserService {
    private final UserRepository userRepository;
    private final RoleRepository roleRepository;

    @Autowired
    public UserServiceImpl(UserRepository userRepository, RoleRepository roleRepository) {
        this.userRepository = userRepository;
        this.roleRepository = roleRepository;
    }

    @Override
    public UserEntity findUserByEmail(String email) {
        return userRepository.findByEmail(email);
    }

    @Override
    public void saveUser(UserRequest userRequest) {
        UserRole userRole = roleRepository.findByName("USER");
        userRepository.save(UserMapper.USER_MAPPER.mapToEntity(userRequest, userRole));
    }

    @Override
    public void followUser(Long userId, String followerName) {
        UserEntity userEntity = userRepository.findById(userId).
                orElseThrow(() -> new UsernameNotFoundException(
                        format(
                                USER_NOT_FOUND_WITH_ID.getMessage(),
                                userId
                        )
                ));
        UserEntity followerEntity = userRepository.findByUsername(followerName).
                orElseThrow(() -> new UsernameNotFoundException(
                        format(
                                USER_NOT_FOUND.getMessage(),
                                followerName
                        )
                ));
        if (!followerEntity.getId().equals(userEntity.getId()) && (!followerEntity.getFollowing().contains(userEntity))) {
            followerEntity.getFollowing().add(userEntity);
            userEntity.setFollowersCount(userEntity.getFollowersCount() + 1);
            followerEntity.setFollowingCount(followerEntity.getFollowingCount() + 1);
            userRepository.save(userEntity);
            userRepository.save(followerEntity);
        } else if (followerEntity.getFollowing().contains(userEntity)) {
            throw new ActionAlreadyDoneException(USER_ALREADY_FOLLOWED.getMessage());
        } else if (followerEntity.getId().equals(userEntity.getId())) {
            throw new SelfFollowException(USER_CANNOT_FOLLOW.getMessage());
        }


    }

    @Override
    public void unfollowUser(Long userId, String followerName) {
        UserEntity userEntity = userRepository.findById(userId).
                orElseThrow(() -> new UsernameNotFoundException(
                        format(
                                USER_NOT_FOUND_WITH_ID.getMessage(),
                                userId
                        )
                ));

        UserEntity followerEntity = userRepository.findByUsername(followerName).
                orElseThrow(() -> new UsernameNotFoundException(
                        format(
                                USER_NOT_FOUND.getMessage(),
                                followerName
                        )
                ));
        if (!followerEntity.getId().equals(userEntity.getId()) && followerEntity.getFollowing().contains(userEntity)) {
            followerEntity.getFollowing().remove(userEntity);
            userEntity.setFollowersCount(userEntity.getFollowersCount() - 1);
            followerEntity.setFollowingCount(followerEntity.getFollowingCount() - 1);
            userRepository.save(userEntity);
            userRepository.save(followerEntity);
        } else if (followerEntity.getId().equals(userEntity.getId())) {
            throw new SelfUnfollowException(USER_CANNOT_UNFOLLOW.getMessage());
        } else if (!followerEntity.getFollowing().contains(userEntity)) {
            throw new ActionAlreadyDoneException(USER_NOT_FOLLOWING.getMessage());
        }

    }
}
