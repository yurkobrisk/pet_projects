package by.korziuk.gradebookapp.service;

import by.korziuk.gradebookapp.model.Teacher;

import java.util.Collection;
import java.util.List;

public interface TeacherService {

    Teacher create(Teacher teacher);

    Collection<Teacher> list(int limit);

    List<Teacher> list();


    Teacher get(String id);

    Teacher update(Teacher teacher);

    Boolean delete(String id);
}
