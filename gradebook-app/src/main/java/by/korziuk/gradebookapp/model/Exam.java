package by.korziuk.gradebookapp.model;

import lombok.Data;
import org.hibernate.annotations.GenericGenerator;

import javax.persistence.*;
import java.util.Date;

@Entity
@Table(name = "T_EXAMS")
public @Data class Exam {

    @Id
    @GeneratedValue(generator = "uuid-generator")
    @GenericGenerator(name = "uuid-generator", strategy = "uuid2")
    @Column(name = "E_ID", nullable = false, updatable = false, unique = true)
    private String id;
    @Column(name = "E_DATE")
    private Date date;
    @Column(name = "E_GRADE")
    private int grade;
    @ManyToOne(cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    @JoinColumn(name = "S_ID")
    private Student student;
}
