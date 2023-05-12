package by.korziuk.gradebookapp.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.GenericGenerator;

import javax.persistence.*;
import javax.validation.constraints.NotEmpty;
import java.io.Serializable;
import java.util.Date;

@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "T_EXAMS")
public class Exam {

    @Id
    @GeneratedValue(generator = "uuid-generator")
    @GenericGenerator(name = "uuid-generator", strategy = "uuid2")
    @Column(name = "E_ID", nullable = false, updatable = false, unique = true)
    private String id;
    @Column(name = "E_DATE")
    @NotEmpty(message = "Date cannot be empty or null")
    private Date date;
    @Column(name = "E_GRADE")
    @NotEmpty(message = "Grade cannot be empty or null")
    private int grade;
    @ManyToOne(cascade = {CascadeType.ALL}, fetch = FetchType.LAZY)
    @JoinColumn(name = "S_ID")
    private Student student;
}
