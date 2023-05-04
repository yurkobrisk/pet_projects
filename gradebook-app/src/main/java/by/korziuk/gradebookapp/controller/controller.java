package by.korziuk.gradebookapp.controller;

import by.korziuk.gradebookapp.model.Teacher;
import by.korziuk.gradebookapp.service.TeacherService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.Collection;

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

    @GetMapping("/teachers/add")
    public String addTeacher(Model model) {
        model.addAttribute("teacher", new Teacher());
        return "add-teacher";
    }

    @PostMapping("/teachers/add")
    public String addTeacher(
            @ModelAttribute("teacher") Teacher teacher
    ) {
        teacherService.create(teacher);
        return "view-teacher";
    }

    @GetMapping("/teachers/{id}")
    public String getTeacher(
            @PathVariable("id") String id,
            Model model
    ) {
        Teacher teacher = teacherService.get(id);
        model.addAttribute("teacher", teacher);
        return "view-teacher";
    }

    @GetMapping("/teachers/{id}/update")
    public String updateTeacher(
            @PathVariable("id") String id,
            Model model
    ) {
        Teacher teacher = teacherService.get(id);
        model.addAttribute("teacher", teacher);
        return "update-teacher";
    }

    @PostMapping("/teachers/{id}/update")
    public String updateTeacher(
            @ModelAttribute("teacher") Teacher teacher
    ) {
        teacherService.update(teacher);
        return "view-teacher";
    }

    @DeleteMapping("/teachers/{id}")
    public String deleteTeacher(
            @PathVariable("id") String id,
            Model model
    ) {
        Boolean delete = teacherService.delete(id);
        model.addAttribute("deleted", delete);
        return "delete-teacher";
    }
}
