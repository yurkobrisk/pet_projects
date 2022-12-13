package by.korziuk.gradebookapp;

import by.korziuk.gradebookapp.data.TeacherRepository;
import by.korziuk.gradebookapp.model.Teacher;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Primary;
import org.springframework.context.annotation.Profile;

import java.util.List;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

@Configuration
@Profile("test")
public class ServiceTestConfig {

    @Bean
    @Primary
    public TeacherRepository teacherRepository() {
        Teacher teacher = new Teacher();
        teacher.setId("5");
        teacher.setName("Teacher 1");

        final TeacherRepository teacherRepositoryMock = mock(TeacherRepository.class);

        when(teacherRepositoryMock.saveAndFlush(any(Teacher.class)))
                .thenReturn(teacher);

        when(teacherRepositoryMock.findAll())
                .thenReturn(List.of(teacher, teacher, teacher));

        when(teacherRepositoryMock.getReferenceById("5"))
                .thenReturn(teacher);

        doAnswer(invocation -> {
            return null;
                }
        ).when(teacherRepositoryMock).deleteById("6");

        return teacherRepositoryMock;
    }

}
