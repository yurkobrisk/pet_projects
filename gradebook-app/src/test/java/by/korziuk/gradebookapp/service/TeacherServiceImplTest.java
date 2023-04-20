package by.korziuk.gradebookapp.service;

import by.korziuk.gradebookapp.ServiceTestConfig;
import by.korziuk.gradebookapp.data.TeacherRepository;
import by.korziuk.gradebookapp.model.Exam;
import by.korziuk.gradebookapp.model.Group;
import by.korziuk.gradebookapp.model.Student;
import by.korziuk.gradebookapp.model.Teacher;
import by.korziuk.gradebookapp.service.implementation.TeacherServiceImpl;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import java.util.Collection;
import java.util.List;

import static org.assertj.core.api.Assertions.*;
import static org.mockito.Mockito.*;

@ActiveProfiles("test")
@ExtendWith(SpringExtension.class)
@ContextConfiguration(classes = {ServiceTestConfig.class, TeacherServiceImpl.class})
class TeacherServiceImplTest {

    @Autowired
    TeacherService teacherService;
    @Autowired
    TeacherRepository teacherRepository;

    @Test
    @DisplayName("it should return teacher when read method runs")
    void readTeacher() {
        //Given
        //When
        Teacher teacher = teacherService.get("5");
        //Then
        assertThat(teacher.getId()).isNotNull();
        assertThat(teacher.getId()).isEqualTo("5");
    }

    @Test
    @DisplayName("it should return teacher when save method runs")
    void saveTeacher() {
        //Given
        Teacher teacher = new Teacher();
        teacher.setName("Teacher 1");
        //When
        Teacher savedTeacher = teacherService.create(teacher);
        //Then
        assertThat(savedTeacher).isNotNull();
        assertThat(savedTeacher.getName()).isEqualTo(teacher.getName());
    }

    @Test
    @DisplayName("it should runs delete method three times")
    void deleteTeacher() {
        //Given
        //When
        teacherService.delete("6");
        teacherService.delete("6");
        teacherService.delete("6");
        //Then
        verify(teacherRepository, times(3))
                .deleteById("6");
    }

    @Test
    @DisplayName("it should return list of teachers with size 3")
    void findAllTeachers() {
        //Given
        //When
        Collection<Teacher> allTeachers = teacherService.list(5);
        //Then
        assertThat(allTeachers).isNotNull();
        assertThat(allTeachers.size()).isEqualTo(3);
        assertThat(allTeachers.iterator().next().getName()).isEqualTo("Teacher 1");
        assertThat(catchNullPointerException(
                () -> allTeachers.iterator().next().getGroups().size())).isInstanceOf(NullPointerException.class);
        assertThat(allTeachers.iterator().next().getSubjectName()).isNull();
    }

    private Teacher create(int id) {
        Teacher teacher = new Teacher();
        teacher.setName("Teacher " + id);
        Group group = new Group();
        group.setName("Group " + id);
        Student student = new Student();
        student.setName("Student " + id);
        Exam exam = new Exam();
        exam.setGrade(8);

        teacher.setGroups(List.of(group, group));
        group.setStudents(List.of(student, student, student, student));
        student.setExams(List.of(exam, exam, exam));

        return teacher;
    }
}
