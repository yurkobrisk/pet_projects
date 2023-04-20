package by.korziuk.gradebookapp.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.GenericGenerator;

import javax.persistence.*;
import javax.validation.constraints.NotEmpty;
import java.util.List;

@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "T_TEACHERS")
public class Teacher {

    @Id
    @GeneratedValue(generator = "uuid-generator")
    @GenericGenerator(name = "uuid-generator", strategy = "uuid2")
    @Column(name = "TEACHER_ID", nullable = false, updatable = false, unique = true)
    private String id;
    @Column(name = "TEACHER_NAME")
    @NotEmpty(message = "Teacher's name cannot be empty or null")
    private String name;
    @Column(name = "TEACHER_LASTNAME")
    @NotEmpty(message = "Teacher's surname cannot be empty or null")
    private String lastName;
    @Column(name = "TEACHER_SUBJECTNAME")
    @NotEmpty(message = "Subject cannot be empty or null")
    private String subjectName;
    @OneToMany(mappedBy = "teacher", cascade = CascadeType.ALL)
    private List<Group> groups;
}
