package az.msblog.entity;

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonManagedReference;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.ArrayList;
import java.util.List;

@Entity
@Builder
@Data
@AllArgsConstructor
@NoArgsConstructor
@Table(name = "posts")
public class PostEntity {
    @Id
    @SequenceGenerator(sequenceName = "post_id", name = "post_id")
    @GeneratedValue(generator = "post_id", strategy = GenerationType.SEQUENCE)
    private Long id;
    private String name;
    private String description;
    @Column(name = "like_count")
    private Long likeCount;
    @Column(name = "comment_count")
    private Long commentCount;


    @ManyToMany
    @JoinTable(name = "liked_posts",
            joinColumns = @JoinColumn(name = "post_id"),
            inverseJoinColumns = @JoinColumn(name = "user_id")
    )
    @JsonBackReference
    private List<UserEntity> likedUsers;


    @ManyToMany
    @JoinTable(name = "post_categories",
            joinColumns = @JoinColumn(name = "post_id"),
            inverseJoinColumns = @JoinColumn(name = "category_id")
    )
    @JsonManagedReference
    private List<CategoryEntity> postCategories;


    @OneToMany(mappedBy = "post")
    @JsonManagedReference
    private List<CommentsEntity> comments;


    @ManyToMany
    @JoinTable(name = "post_tags",
            joinColumns = @JoinColumn(name = "post_id"),
            inverseJoinColumns = @JoinColumn(name = "tag_id"))
    @JsonManagedReference
    private List<TagEntity> tags;


}
