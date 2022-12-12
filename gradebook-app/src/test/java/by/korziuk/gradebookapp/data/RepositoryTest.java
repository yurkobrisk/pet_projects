package by.korziuk.gradebookapp.data;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.jdbc.Sql;

import java.util.Objects;

import static org.assertj.core.api.Assertions.*;
import static org.springframework.test.context.jdbc.Sql.ExecutionPhase.*;

@ActiveProfiles("test")
@DataJpaTest()
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE)
@Sql(scripts = "/create-data.sql")
@Sql(scripts = "/cleanup-data.sql", executionPhase = AFTER_TEST_METHOD)
class RepositoryTest {

    @Autowired
    TeacherRepository teacherRepository;
    @Autowired
    GroupRepository groupRepository;
    @Autowired
    StudentRepository studentRepository;
    @Autowired
    ExamRepository examRepository;

    @Test
    public void should_read_teachers_from_table() {
        assertThat((Objects
                .requireNonNull(teacherRepository.findById("1").orElse(null)))
                .getName()).isEqualTo("Alex");
        assertThat((Objects
                .requireNonNull(teacherRepository.findById("2").orElse(null)))
                .getSubjectName()).isEqualTo("Italian");
        assertThat(teacherRepository.findAll().size()).isEqualTo(2);
    }

    @Test
    public void should_read_groups_from_table() {
        assertThat((Objects
                .requireNonNull(groupRepository.findById("group2").orElse(null)))
                .getName()).isEqualTo("group B");
        assertThat((Objects
                .requireNonNull(groupRepository.findById("group2").orElse(null)))
                .getTeacher().getId()).isEqualTo("1");
        assertThat(groupRepository.findAll().size()).isEqualTo(2);
    }

    @Test
    public void should_read_students_from_table() {
        assertThat((Objects
                .requireNonNull(studentRepository.findById("student3").orElse(null)))
                .getLastName()).isEqualTo("Prohorov");
        assertThat(studentRepository.findAll().size()).isEqualTo(4);
    }

    @Test
    public void should_read_exams_from_table() {
        assertThat((Objects
                .requireNonNull(examRepository.findById("exam4").orElse(null)))
                .getGrade()).isEqualTo(5);
        assertThat(examRepository.findAll().size()).isEqualTo(4);
    }
}
