package by.korziuk.gradebookapp.data;

import by.korziuk.gradebookapp.model.Student;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface StudentRepository extends JpaRepository<Student, String> {
    Student findStudentById(String id);
    List<Student> findStudentsByGroupId(String id);
}
