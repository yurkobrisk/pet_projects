package by.korziuk.gradebookapp.controller;

import by.korziuk.gradebookapp.model.Group;
import by.korziuk.gradebookapp.model.Student;
import by.korziuk.gradebookapp.model.Teacher;
import by.korziuk.gradebookapp.service.GroupService;
import by.korziuk.gradebookapp.service.TeacherService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.Collection;
import java.util.List;

@Controller
@RequestMapping("/gradebook/groups")
@RequiredArgsConstructor
public class GroupController {

    private final GroupService groupService;
    private final TeacherService teacherService;

    @GetMapping("")
    public String getAllGroups(Model model) {
        Collection<Group> groups = groupService.list();
        model.addAttribute("listOfGroups", groups);
        return "groups";
    }

    @GetMapping("/add")
    public String addGroup(Model model) {
        List<Teacher> teachers = teacherService.list();
        model.addAttribute("group", new Group());
        model.addAttribute("teachers", teachers);
        return "add-group";
    }

    @PostMapping("/add")
    public String addGroup(
            @ModelAttribute("group") Group group
    ) {
        groupService.create(group);
        return "view-group";
    }
}
