package by.korziuk.gradebookapp.service;

import by.korziuk.gradebookapp.model.Teacher;

import java.util.List;

public interface TeacherService {

    Teacher readTeacher(String id);

    Teacher saveTeacher(Teacher teacher);

    Teacher updateTeacher(Teacher teacher);

    void deleteTeacher(String id);

    List<Teacher> findAllTeachers();
}
