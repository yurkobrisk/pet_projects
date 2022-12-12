package by.korziuk.gradebookapp.service;

import by.korziuk.gradebookapp.ServiceTestConfig;
import by.korziuk.gradebookapp.data.TeacherRepository;
import by.korziuk.gradebookapp.model.Exam;
import by.korziuk.gradebookapp.model.Group;
import by.korziuk.gradebookapp.model.Student;
import by.korziuk.gradebookapp.model.Teacher;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import java.util.List;

import static org.assertj.core.api.Assertions.*;

@ActiveProfiles("test")
@ExtendWith(SpringExtension.class)
@ContextConfiguration(classes = {ServiceTestConfig.class, TeacherServiceImpl.class})
class TeacherServiceImplTest {

    @Autowired
    TeacherService teacherService;
    @Autowired
    TeacherRepository teacherRepository;

    @Test
    void readTeacher() {
        //Given
        //When
        //Then
    }

    @Test
    @DisplayName("it should return teacher when save method runs")
    void saveTeacher() {
        //Given
        Teacher teacher = new Teacher();
        teacher.setName("Teacher 1");
        //When
        Teacher savedTeacher = teacherService.saveTeacher(teacher);
        //Then
        assertThat(savedTeacher).isNotNull();
        assertThat(savedTeacher.getName()).isEqualTo(teacher.getName());
    }

    @Test
    void deleteTeacher() {
    }

    @Test
    void findAllTeachers() {
        //Given
        Teacher teacher1 = create(1);
        Teacher teacher2 = create(2);
        Teacher teacher3 = create(3);
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
