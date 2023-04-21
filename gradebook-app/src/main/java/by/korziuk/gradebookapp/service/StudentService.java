package by.korziuk.gradebookapp.service;

import by.korziuk.gradebookapp.model.Student;

import java.util.Collection;

public interface StudentService {

    Student create(Student student);

    Collection<Student> list(int limit);

    Student get(String id);

    Student update(Student student);

    Boolean delete(String id);
}
