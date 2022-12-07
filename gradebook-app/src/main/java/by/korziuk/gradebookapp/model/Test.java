package by.korziuk.gradebookapp.model;

import lombok.Data;
import org.hibernate.annotations.GenericGenerator;

import javax.persistence.*;
import java.io.Serializable;
import java.util.Date;

@Entity
@Table(name = "T_TESTS")
public @Data class Test implements Serializable {

    @Id
    @GeneratedValue(generator = "uuid-generator")
    @GenericGenerator(name = "uuid-generator", strategy = "uuid2")
    @Column(name = "TEST_ID", nullable = false)
    private String id;
    @Column(name = "TEST_DATE")
    private Date date;
    @Column(name = "TEST_GRADE")
    private int grade;
    @ManyToOne(cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    @JoinColumn(name = "S_ID")
    private Student student;
}
