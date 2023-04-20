package by.korziuk.gradebookapp.service;

import by.korziuk.gradebookapp.model.Teacher;

import java.util.Collection;

public interface TeacherService {

    Teacher create(Teacher teacher);

    Collection<Teacher> list(int limit);

    Teacher get(String id);

    Teacher update(Teacher teacher);

    Boolean delete(String id);
}
