package by.korziuk.gradebookapp;

import by.korziuk.gradebookapp.data.StudentRepository;
import by.korziuk.gradebookapp.data.TeacherRepository;
import by.korziuk.gradebookapp.model.Student;
import by.korziuk.gradebookapp.model.Teacher;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Primary;
import org.springframework.context.annotation.Profile;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;

import java.util.List;
import java.util.Optional;

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
        Page<Teacher> page = new PageImpl<>(List.of(teacher, teacher, teacher));

        when(teacherRepositoryMock.save(any(Teacher.class)))
                .thenReturn(teacher);

        when(teacherRepositoryMock.findAll(any(PageRequest.class)))
                .thenReturn(page);

        when(teacherRepositoryMock.findById("5"))
                .thenReturn(Optional.of(teacher));

        doAnswer(invocation -> {
            return null;
                }
        ).when(teacherRepositoryMock).deleteById("6");

        return teacherRepositoryMock;
    }

    @Bean
    @Primary
    public StudentRepository studentRepository() {
        Student student = new Student();
        student.setId("4");
        student.setName("Student 1");

        final StudentRepository studentRepositoryMock = mock(StudentRepository.class);
        Page<Student> page = new PageImpl<>(List.of(student, student, student, student));

        when(studentRepositoryMock.save(any(Student.class)))
                .thenReturn(student);

        when(studentRepositoryMock.findAll(any(PageRequest.class)))
                .thenReturn(page);

        when(studentRepositoryMock.findById("4"))
                .thenReturn(Optional.of(student));

        doAnswer(invocation -> {
                    return null;
                }
        ).when(studentRepositoryMock).deleteById("6");

        return studentRepositoryMock;
    }

}
