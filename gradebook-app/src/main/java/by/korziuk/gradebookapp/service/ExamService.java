package by.korziuk.gradebookapp.service;

import by.korziuk.gradebookapp.model.Exam;

import java.util.List;

public interface ExamService {

    Exam create(Exam exam);

    List<Exam> list();

    Exam get(String id);

    Exam update(Exam exam);

    Boolean delete(String id);
}
