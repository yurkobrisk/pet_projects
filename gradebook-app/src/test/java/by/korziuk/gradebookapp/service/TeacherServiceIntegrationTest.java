package by.korziuk.gradebookapp.service;

import by.korziuk.gradebookapp.GradebookApplication;
import by.korziuk.gradebookapp.data.TeacherRepository;
import by.korziuk.gradebookapp.model.Teacher;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit.jupiter.SpringExtension;

@ActiveProfiles("test")
@ExtendWith(SpringExtension.class)
@ContextConfiguration(classes = {GradebookApplication.class})
public class TeacherServiceIntegrationTest {

//    @Autowired
//    TeacherService teacherService;

    @Autowired
    TeacherRepository teacherRepository;

    @Test
    public void should_save_teacher_to_test_db() {
        //Given
        Teacher teacher = new Teacher();
        teacher.setName("Mike");
        //When
        teacherRepository.save(teacher);
        //Then
    }
}
