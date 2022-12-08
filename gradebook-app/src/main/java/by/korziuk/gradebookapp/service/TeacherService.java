package by.korziuk.gradebookapp.service;

import by.korziuk.gradebookapp.model.Teacher;

public interface TeacherService {

    Teacher readTeacher(String id);

    Teacher saveTeacher(Teacher teacher);

    Teacher updateTeacher(Teacher teacher);

    void deleteTeacher(String id);
}
