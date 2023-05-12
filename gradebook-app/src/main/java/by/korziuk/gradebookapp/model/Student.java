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
@Table(name = "T_STUDENTS")
public class Student {

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
    @OneToMany(mappedBy = "student")
    private List<Exam> exams;
    @ManyToOne(cascade = {CascadeType.ALL}, fetch = FetchType.LAZY)
    @JoinColumn(name = "G_ID")
    private Group group;
}
