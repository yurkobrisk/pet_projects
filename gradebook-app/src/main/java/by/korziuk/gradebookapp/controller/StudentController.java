package by.korziuk.gradebookapp.controller;

import by.korziuk.gradebookapp.model.Group;
import by.korziuk.gradebookapp.model.Student;
import by.korziuk.gradebookapp.service.GroupService;
import by.korziuk.gradebookapp.service.StudentService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.Collection;

@Controller
@RequestMapping("/gradebook/students")
@RequiredArgsConstructor
public class StudentController {

    private final StudentService studentService;
    private final GroupService groupService;

    @GetMapping("")
    public String getAllStudents(
            @RequestParam(value = "limit", defaultValue = "10") int limit,
            Model model) {
        Collection<Student> students = studentService.list(limit);
        model.addAttribute("listOfStudents", students);
        return "students";
    }

    @GetMapping("/add")
    public String addStudent(Model model) {
        Collection<Group> groups = groupService.list();
        model.addAttribute("student", new Student());
        model.addAttribute("groups", groups);
        return "add-student";
    }

    @PostMapping("/add")
    public String addStudent(
            @ModelAttribute("student") Student student
    ) {
        studentService.create(student);
        return "view-student";
    }

    @GetMapping("/{id}")
    public String getStudent(
            @PathVariable("id") String id,
            Model model
    ) {
        Student student = studentService.get(id);
        model.addAttribute("student", student);
        return "view-student";
    }

    @GetMapping("/{id}/update")
    public String updateStudent(
            @PathVariable("id") String id,
            Model model
    ) {
        Student student = studentService.get(id);
        model.addAttribute("student", student);
        return "update-student";
    }

    @PostMapping("/{id}/update")
    public String updateStudent(
            @ModelAttribute("student") Student student
    ) {
        studentService.update(student);
        return "view-student";
    }

    @DeleteMapping("/{id}")
    public String deleteStudent(
            @PathVariable("id") String id,
            Model model
    ) {
        Boolean delete = studentService.delete(id);
        model.addAttribute("deleted", delete);
        return "delete-student";
    }
}
