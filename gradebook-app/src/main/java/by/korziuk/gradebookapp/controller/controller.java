package by.korziuk.gradebookapp.controller;

import by.korziuk.gradebookapp.model.Group;
import by.korziuk.gradebookapp.model.Teacher;
import by.korziuk.gradebookapp.service.TeacherService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.Collection;
import java.util.List;

@Controller
@RequestMapping("/gradebook")
@RequiredArgsConstructor
public class controller {

    private final TeacherService teacherService;

    @GetMapping("/teachers")
    public String getAllTeachers(
            @RequestParam(value = "limit", defaultValue = "10") int limit,
            Model model) {
        Collection<Teacher> teachers = teacherService.list(limit);
        model.addAttribute("listOfTeachers", teachers);
        return "index";
    }

    @PostMapping("/teachers")
    public String addTeacher() {

        Teacher teacher = new Teacher();
        teacher.setName("Petr");
        teacher.setLastName("Ivanov");
        teacher.setSubjectName("English");
        Group group1 = new Group();
        group1.setName("group 1");
        Group group2 = new Group();
        group2.setName("group 2");
        teacher.setGroups(List.of(group1, group2));

        teacherService.create(teacher);
        return "index";
    }

    @GetMapping("/teachers/add")
    public String addTeacher(Model model) {
        model.addAttribute("teacher", new Teacher());
        return "add-teacher";
    }
}
