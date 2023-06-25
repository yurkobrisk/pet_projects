package by.korziuk.gradebookapp.data;

import by.korziuk.gradebookapp.model.Exam;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ExamRepository extends JpaRepository<Exam, String> {
}
