package by.korziuk.gradebookapp.service;

import by.korziuk.gradebookapp.model.Group;

import java.util.List;

public interface GroupService {

    Group create(Group group);

    List<Group> list();

    Group get(String id);

    Group update(Group group);

    Boolean delete(String id);

    Boolean existsGroupByName(String name);
}
