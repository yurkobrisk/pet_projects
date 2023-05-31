package by.korziuk.gradebookapp.controller;

import by.korziuk.gradebookapp.dto.GroupExamsDTO;
import by.korziuk.gradebookapp.model.Group;
import by.korziuk.gradebookapp.model.Student;
import by.korziuk.gradebookapp.model.Teacher;
import by.korziuk.gradebookapp.service.GroupService;
import by.korziuk.gradebookapp.service.StudentService;
import by.korziuk.gradebookapp.service.TeacherService;
import by.korziuk.gradebookapp.dto.GroupDTO;
import by.korziuk.gradebookapp.service.mapper.MapService;
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
    private final StudentService studentService;
    private final MapService mapService;

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
        if (groupService.existsGroupByName(group.getName())) {
            return "view-group";
        }
        groupService.create(group);
        return "view-group";
    }

    @GetMapping("/{id}")
    public String getGroup(
            @PathVariable("id") String id,
            Model model
    ) {
        Group group = groupService.get(id);
        model.addAttribute("group", group);
        return "view-group";
    }

    @GetMapping("/{id}/update")
    public String updateGroup(
        @PathVariable("id") String id,
        Model model
) {
    Group group = groupService.get(id);
    GroupDTO dto = mapService.toDto(group);
    List<Teacher> teachers = teacherService.list();
    List<Student> students = studentService.list();
    model.addAttribute("dto", dto);
    model.addAttribute("teachers", teachers);
    model.addAttribute("students", students);
    return "update-group";
}

    @PostMapping("/{id}/update")
    public String updateGroup(
            @ModelAttribute("dto") GroupDTO dto,
            Model model
    ) {
        Group group = mapService.toGroup(dto);
        List<Student> studentsStored = studentService.findStudentsByGroupId(group.getId());
        for (Student student : group.getStudents()) {
            if (studentsStored.contains(student)) {
                studentsStored.remove(student);
                continue;
            }
            studentService.update(student);
        }
        studentsStored.forEach(student -> student.setGroup(null));

        model.addAttribute("group", group);
        groupService.update(group);
        return "view-group";
    }

    @DeleteMapping("/{id}")
    public String deleteGroup(
            @PathVariable("id") String id,
            Model model
    ) {
        Boolean delete = groupService.delete(id);
        model.addAttribute("deleted", delete);
        return "delete-group";
    }

    @GetMapping("/{id}/exams")
    public String getGroupExams(
            @PathVariable("id") String id,
            Model model
    ) {
        Group group = groupService.get(id);
        GroupExamsDTO dto = mapService.toExamsDto(group);
        model.addAttribute("dto", dto);
        return "view-group-exams";
    }
}
