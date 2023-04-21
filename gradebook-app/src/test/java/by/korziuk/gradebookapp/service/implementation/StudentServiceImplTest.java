package by.korziuk.gradebookapp.service.implementation;

import by.korziuk.gradebookapp.ServiceTestConfig;
import by.korziuk.gradebookapp.data.StudentRepository;
import by.korziuk.gradebookapp.model.Student;
import by.korziuk.gradebookapp.service.StudentService;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import java.util.Collection;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.catchNullPointerException;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;

@ActiveProfiles("test")
@ExtendWith(SpringExtension.class)
@ContextConfiguration(classes = {ServiceTestConfig.class, StudentServiceImpl.class})
class StudentServiceImplTest {

    @Autowired
    StudentService studentService;
    @Autowired
    StudentRepository studentRepository;

    @Test
    @DisplayName("it should return student when get method runs")
    void getStudent() {
        //Given
        //When
        Student student = studentService.get("4");
        //Then
        assertThat(student.getId()).isNotNull();
        assertThat(student.getId()).isEqualTo("4");
    }

    @Test
    @DisplayName("it should return student when create method runs")
    void createStudent() {
        //Given
        Student student = new Student();
        student.setName("Student 1");
        //When
        Student savedTeacher = studentService.create(student);
        //Then
        assertThat(savedTeacher).isNotNull();
        assertThat(savedTeacher.getName()).isEqualTo(student.getName());
    }

    @Test
    @DisplayName("it should runs delete method three times")
    void deleteStudent() {
        //Given
        //When
        studentService.delete("6");
        studentService.delete("6");
        studentService.delete("6");
        //Then
        verify(studentRepository, times(3))
                .deleteById("6");
    }

    @Test
    @DisplayName("it should return list of students with size 4")
    void findAllStudents() {
        //Given
        //When
        Collection<Student> students = studentService.list(5);
        //Then
        assertThat(students).isNotNull();
        assertThat(students.size()).isEqualTo(4);
        assertThat(students.iterator().next().getName()).isEqualTo("Student 1");
        assertThat(catchNullPointerException(
                () -> students.iterator().next().getExams().size())).isInstanceOf(NullPointerException.class);
        assertThat(students.iterator().next().getGroup()).isNull();
    }
}
