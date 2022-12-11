package by.korziuk.gradebookapp.data;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.jdbc.Sql;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import java.util.Objects;

import static org.assertj.core.api.Assertions.*;
import static org.springframework.test.context.jdbc.Sql.ExecutionPhase.*;

@ActiveProfiles("test")
@DataJpaTest()
@ExtendWith(SpringExtension.class)
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE)
@Sql(scripts = "/create-teacher.sql")
@Sql(scripts = "/cleanup-teacher.sql", executionPhase = AFTER_TEST_METHOD)
class TeacherRepositoryHSQLDBTest {

    @Autowired
    TeacherRepository teacherRepository;

    @Test
    public void should_create_db_in_memory() {
        //Given
        //When
        //Then
        assertThat((Objects
                .requireNonNull(teacherRepository.findById("1").orElse(null)))
                .getName()).isEqualTo("Alex");
        assertThat(teacherRepository.findAll().size()).isEqualTo(2);
    }
}
