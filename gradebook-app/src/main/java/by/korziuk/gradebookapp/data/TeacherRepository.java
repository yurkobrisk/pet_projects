package by.korziuk.gradebookapp.data;

import by.korziuk.gradebookapp.model.Teacher;
import org.springframework.data.jpa.repository.JpaRepository;

public interface TeacherRepository extends JpaRepository<Teacher, String> {
}
