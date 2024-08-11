package az.msblog.entity;


import com.fasterxml.jackson.annotation.JsonBackReference;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "roles")
public class UserRole {
    @Id
    @SequenceGenerator(name = "role_id", sequenceName = "role_id")
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "role_id")
    private Long id;
    private String name;

    @ManyToMany(mappedBy = "roles")
    @JsonBackReference
    private List<UserEntity> users;
}
