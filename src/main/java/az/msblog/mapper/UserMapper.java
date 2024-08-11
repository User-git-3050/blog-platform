package az.msblog.mapper;

import az.msblog.dao.request.UserRequest;
import az.msblog.entity.UserEntity;
import az.msblog.entity.UserRole;
import az.msblog.repository.RoleRepository;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

import java.util.Set;

public enum UserMapper {
    USER_MAPPER;

    public UserEntity mapToEntity(UserRequest userRequest, UserRole role) {
        return UserEntity.builder().
                username(userRequest.getUsername())
                .password(new BCryptPasswordEncoder().encode(userRequest.getPassword()))
                .email(userRequest.getEmail())
                .roles(Set.of(role))
                .followersCount(0L)
                .followingCount(0L)
                .build();
    }

}
