package az.msblog.entity;

import com.fasterxml.jackson.annotation.JsonManagedReference;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.HashSet;
import java.util.List;
import java.util.Set;

@Entity
@Builder
@Data
@AllArgsConstructor
@NoArgsConstructor
@Table(name = "users")
public class UserEntity {
    @Id
    @SequenceGenerator(sequenceName = "user_id", name = "user_id")
    @GeneratedValue(generator = "user_id", strategy = GenerationType.SEQUENCE)
    private Long id;
    @Column(name = "followers_count")
    private Long followersCount;
    @Column(name = "following_count")
    private Long followingCount;
    private String username;
    private String password;
    private String email;

    @ManyToMany
    @JoinTable(name = "liked_posts",
            joinColumns = @JoinColumn(name = "user_id"),
            inverseJoinColumns = @JoinColumn(name = "post_id")
    )
    @JsonManagedReference
    private List<PostEntity> likedPosts;


    @OneToMany(mappedBy = "user")
    @JsonManagedReference
    private List<CommentsEntity> comments;


    @ManyToMany
    @JoinTable(name = "user_following",
            joinColumns = @JoinColumn(name = "user_id"),
            inverseJoinColumns = @JoinColumn(name = "following_id"))
    private List<UserEntity> following;


    @ManyToMany(fetch = FetchType.EAGER)
    @JoinTable(
            name = "user_roles",
            joinColumns = @JoinColumn(name = "user_id"),
            inverseJoinColumns = @JoinColumn(name = "role_id")
    )
    private Set<UserRole> roles = new HashSet<>();


}
