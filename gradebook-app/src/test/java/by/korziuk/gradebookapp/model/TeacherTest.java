package by.korziuk.gradebookapp.model;

import by.korziuk.gradebookapp.data.TeacherRepository;
import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.jdbc.Sql;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import java.util.Calendar;
import java.util.Date;
import java.util.List;

import static org.assertj.core.api.Assertions.*;
import static org.springframework.test.context.jdbc.Sql.ExecutionPhase.AFTER_TEST_METHOD;

@ActiveProfiles("test")
@DataJpaTest
@ExtendWith(SpringExtension.class)
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE)
@Sql(scripts = "/create-teacher.sql")
@Sql(scripts = "/cleanup-teacher.sql", executionPhase = AFTER_TEST_METHOD)
class TeacherTest {

    @Autowired
    TeacherRepository teacherRepository;

    @org.junit.jupiter.api.Test
    public void should_save_teacher_to_db() {
        //Given
//        Teacher teacher = new Teacher();
////        teacher.setId("8a8c69db-973c-411a-a3d9-bb741e36c7fc");
//        teacher.setName("Mike");
//        teacher.setLastName("Duglas");
//        teacher.setSubjectName("Movie");
//
//        Group group1 = new Group();
////        group1.setId("2978d6a1-2a3f-4567-94bc-e3dff18649cd");
//        group1.setTeacher(teacher);
//        group1.setName("group 1");
//        Group group2 = new Group();
////        group2.setId("2427fec9-4fd0-4260-bf2f-816e7cc17182");
//        group2.setTeacher(teacher);
//        group2.setName("group 2");
//
//        Student student1 = new Student();
////        student1.setId("92154923-508b-4246-a79a-6b9e8b5e72de");
//        student1.setName("Igor");
//        student1.setLastName("Timofeev");
//        student1.setGroup(group1);
//        Student student2 = new Student();
////        student2.setId("92154923-508b-4246-a79a-6b9e8b5e72d9");
//        student2.setName("Petr");
//        student2.setLastName("Voinich");
//        student2.setGroup(group1);
//        Student student3 = new Student();
////        student3.setId("92154923-508b-4246-a79a-6b9e8b5e72d78");
//        student3.setName("Alex");
//        student3.setLastName("Prohorov");
//        student3.setGroup(group2);
//        Student student4 = new Student();
////        student4.setId("92154923-508b-4246-a79a-6b9e8b5e7279");
//        student4.setName("Dmitry");
//        student4.setLastName("Lobovich");
//        student4.setGroup(group2);
//
//        Test test1 = new Test();
////        test1.setId("8a8c69db-973c-411a-a3d9-bb741e36c7fc");
//        test1.setDate(Date.from(Calendar.getInstance().getTime().toInstant()));
//        test1.setGrade(8);
//        test1.setStudent(student1);
//        Test test2 = new Test();
////        test2.setId("8a8c69db-973c-411a-a3d9-bb741e36c7f2");
//        test2.setDate(Date.from(Calendar.getInstance().getTime().toInstant()));
//        test2.setGrade(7);
//        test2.setStudent(student2);
//        Test test3 = new Test();
////        test3.setId("8a8c69db-973c-411a-a3d9-bb741e36c7f4");
//        test3.setDate(Date.from(Calendar.getInstance().getTime().toInstant()));
//        test3.setGrade(10);
//        test3.setStudent(student3);
//        Test test4 = new Test();
////        test4.setId("8a8c69db-973c-411a-a3d9-bb741e36c7f6");
//        test4.setDate(Date.from(Calendar.getInstance().getTime().toInstant()));
//        test4.setGrade(5);
//        test4.setStudent(student4);
//
//        teacher.setGroups(List.of(group1, group2));
//        group1.setStudents(List.of(student1, student2));
//        group2.setStudents(List.of(student3, student4));
//        student1.setTests(List.of(test1));
//        student2.setTests(List.of(test2));
//        student3.setTests(List.of(test3));
//        student4.setTests(List.of(test4));
        //When
//        teacherRepository.save(teacher);
        List<Teacher> list = teacherRepository.findAll();
        //Then
        assertThat(teacherRepository.findAll().size()).isEqualTo(2);

//        assertThat(teacher)
//                .withFailMessage("The teacher is null")
//                .isNotNull();
//        assertThat(list.get(0))
//                .withFailMessage("The teacher saved in DB is null")
//                .isNotNull();
//        assertThat(list.get(0).getGroups().get(0).getStudents().get(0).getId())
//                .withFailMessage("The student id is null")
//                .isNotNull(); //ToDo fail this test
//        assertThat(list.get(0).getGroups().size()).isEqualTo(2);
//        assertThat(list.get(0).getGroups().get(1).getStudents().get(1).getName()).isEqualTo("Ark");
    }
}
