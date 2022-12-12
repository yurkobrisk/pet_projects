package by.korziuk.gradebookapp.model;

import lombok.Data;
import org.hibernate.annotations.GenericGenerator;

import javax.persistence.*;
import java.util.List;

@Entity
@Table(name = "T_STUDENTS")
public @Data class Student {

    @Id
    @GeneratedValue(generator = "uuid-generator")
    @GenericGenerator(name = "uuid-generator", strategy = "uuid2")
    @Column(name = "S_ID", nullable = false, unique = true, updatable = false)
    private String id;
    @Column(name = "S_NAME")
    private String name;
    @Column(name = "S_LASTNAME")
    private String lastName;
    @OneToMany(mappedBy = "student", cascade = CascadeType.ALL)
    private List<Exam> exams;
    @ManyToOne(cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    @JoinColumn(name = "G_ID")
    private Group group;
}
