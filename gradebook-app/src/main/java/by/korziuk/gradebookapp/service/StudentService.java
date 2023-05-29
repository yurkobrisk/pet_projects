package by.korziuk.gradebookapp.service;

import by.korziuk.gradebookapp.model.Student;

import java.util.Collection;
import java.util.List;

public interface StudentService {

    Student create(Student student);

    Collection<Student> list(int limit);

    List<Student> list();

    Student get(String id);

    Student update(Student student);

    Boolean delete(String id);
}
