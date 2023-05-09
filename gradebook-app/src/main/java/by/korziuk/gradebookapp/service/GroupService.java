package by.korziuk.gradebookapp.service;

import by.korziuk.gradebookapp.model.Group;
import by.korziuk.gradebookapp.model.Student;

import java.util.Collection;

public interface GroupService {

    Group create(Group group);

    Collection<Group> list();
}
