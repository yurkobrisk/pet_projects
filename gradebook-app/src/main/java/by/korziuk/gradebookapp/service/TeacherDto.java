package by.korziuk.gradebookapp.service;


import by.korziuk.gradebookapp.model.Group;
import lombok.Data;

import java.util.List;

public @Data class TeacherDto {

    private String name;

    private String lastName;

    private String subjectName;

    private List<Group> groups;
}
