package az.msblog.entity;

import com.fasterxml.jackson.annotation.JsonBackReference;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;
import java.util.List;

@Entity
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Data
@Table(name = "tags")
public class TagEntity {
    @Id
    @SequenceGenerator(name = "tag_id", sequenceName = "tag_id", allocationSize = 1)
    @GeneratedValue(generator = "tag_id", strategy = GenerationType.SEQUENCE)
    private Long id;
    private String name;
    private String description;
    private LocalDateTime created;
    private LocalDateTime modified;

    @ManyToMany(mappedBy = "tags")
    @JsonBackReference
    private List<PostEntity> posts;

}
