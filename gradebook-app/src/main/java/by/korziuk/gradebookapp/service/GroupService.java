package by.korziuk.gradebookapp.service;

import by.korziuk.gradebookapp.model.Group;
import by.korziuk.gradebookapp.model.Student;
import by.korziuk.gradebookapp.model.Teacher;

import java.util.Collection;
import java.util.List;

public interface GroupService {

    Group create(Group group);

    Collection<Group> list();

    Group get(String id);

    Group update(Group group);

    Boolean delete(String id);
}
