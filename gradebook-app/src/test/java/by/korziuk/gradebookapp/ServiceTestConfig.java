package by.korziuk.gradebookapp;

import by.korziuk.gradebookapp.data.TeacherRepository;
import by.korziuk.gradebookapp.model.Teacher;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Primary;
import org.springframework.context.annotation.Profile;

import java.util.Optional;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

@Configuration
@Profile("test")
public class ServiceTestConfig {

    @Bean
    @Primary
    public TeacherRepository teacherRepository() {
        Teacher teacher = new Teacher();
        teacher.setName("Teacher 1");

        final TeacherRepository teacherRepositoryMock = mock(TeacherRepository.class);

        when(teacherRepositoryMock.saveAndFlush(any(Teacher.class)))
                .thenReturn(teacher);

        when(Optional.of(teacherRepositoryMock.findById("5")).orElse(null))
                .thenReturn(Optional.of(teacher));

        return teacherRepositoryMock;
    }

}
