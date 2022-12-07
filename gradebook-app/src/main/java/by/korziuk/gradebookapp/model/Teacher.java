package by.korziuk.gradebookapp.model;

import lombok.Data;
import org.hibernate.annotations.GenericGenerator;

import javax.persistence.*;
import java.io.Serializable;
import java.util.List;

@Entity
@Table(name = "T_TEACHER")
public @Data class Teacher implements Serializable {

    @Id
    @GeneratedValue(generator = "uuid-generator")
    @GenericGenerator(name = "uuid-generator", strategy = "uuid2")
    @Column(name = "TEACHER_ID", nullable = false)
    private String id;
    @Column(name = "TEACHER_NAME")
    private String name;
    @Column(name = "TEACHER_LASTNAME")
    private String lastName;
    @Column(name = "TEACHER_SUBJECTNAME")
    private String subjectName;
    @OneToMany(mappedBy = "teacher")
    private List<Group> groups;
}
