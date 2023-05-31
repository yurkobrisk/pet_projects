package by.korziuk.gradebookapp.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.GenericGenerator;

import javax.persistence.*;
import javax.validation.constraints.NotEmpty;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "T_GROUPS")
public class Group implements Serializable{

    @Id
    @GeneratedValue(generator = "uuid-generator")
    @GenericGenerator(name = "uuid-generator", strategy = "uuid2")
    @Column(name = "G_ID", nullable = false, unique = true, updatable = false)
    private String id;
    @Column(name = "G_NAME", unique = true)
    @NotEmpty(message = "Group name cannot be empty or null")
    private String name;
    @OneToMany(mappedBy = "group", cascade = CascadeType.ALL, orphanRemoval = false)
    private List<Student> students = new ArrayList<>();
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "TEACHER_ID")
    private Teacher teacher;

    public void addStudent(Student student) {
        this.students.add(student);
        student.setGroup(this);
    }

    public void removeStudent(Student student) {
        this.students.remove(student);
        student.setGroup(null);
    }
}
