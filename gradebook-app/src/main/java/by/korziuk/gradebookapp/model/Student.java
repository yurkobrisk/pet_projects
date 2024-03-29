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
@Table(name = "T_STUDENTS")
public class Student implements Serializable{

    @Id
    @GeneratedValue(generator = "uuid-generator")
    @GenericGenerator(name = "uuid-generator", strategy = "uuid2")
    @Column(name = "S_ID", nullable = false, unique = true, updatable = false)
    private String id;
    @Column(name = "S_NAME")
    @NotEmpty(message = "Name cannot be empty or null")
    private String name;
    @Column(name = "S_LASTNAME")
    @NotEmpty(message = "Surname cannot be empty or null")
    private String lastName;
    @OneToMany(mappedBy = "student", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<Exam> exams = new ArrayList<>();
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "G_ID")
    private Group group;

    public void addExam(Exam exam) {
        exams.add(exam);
        exam.setStudent(this);
    }

    public void removeExam(Exam exam) {
        exams.remove(exam);
        exam.setStudent(null);
    }
}
