package by.korziuk.gradebookapp.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.GenericGenerator;

import javax.persistence.*;
import javax.validation.constraints.NotEmpty;
import java.io.Serializable;
import java.util.List;

@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "T_GROUPS")
public class Group {

    @Id
    @GeneratedValue(generator = "uuid-generator")
    @GenericGenerator(name = "uuid-generator", strategy = "uuid2")
    @Column(name = "G_ID", nullable = false, unique = true, updatable = false)
    private String id;
    @Column(name = "G_NAME", unique = true)
    @NotEmpty(message = "Group name cannot be empty or null")
    private String name;
    @OneToMany(mappedBy = "group")
    private List<Student> students;
    @ManyToOne
    @JoinColumn(name = "TEACHER_ID")
    private Teacher teacher;
}
