package az.msblog.entity;

import com.fasterxml.jackson.annotation.JsonBackReference;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Entity
@Builder
@AllArgsConstructor
@NoArgsConstructor
@Data
@Table(name = "comments")
public class CommentsEntity {
    @Id
    @SequenceGenerator(sequenceName = "comment_id", name = "comment_id")
    @GeneratedValue(generator = "comment_id", strategy = GenerationType.SEQUENCE)
    private Long id;
    private String description;
    private LocalDateTime created;
    private LocalDateTime modified;


    @ManyToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "post_id")
    @JsonBackReference
    private PostEntity post;


    @ManyToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "user_id")
    @JsonBackReference
    private UserEntity user;


}
