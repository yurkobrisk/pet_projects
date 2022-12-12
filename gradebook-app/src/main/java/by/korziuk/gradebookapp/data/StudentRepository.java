package by.korziuk.gradebookapp.data;

import by.korziuk.gradebookapp.model.Student;
import org.springframework.data.jpa.repository.JpaRepository;

public interface StudentRepository extends JpaRepository<Student, String> {
}
