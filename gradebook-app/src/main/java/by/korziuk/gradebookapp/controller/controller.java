package by.korziuk.gradebookapp.controller;

import by.korziuk.gradebookapp.model.Teacher;
import by.korziuk.gradebookapp.service.Mapper;
import by.korziuk.gradebookapp.service.TeacherDto;
import by.korziuk.gradebookapp.service.TeacherService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;

import java.util.List;

@Controller
public class controller {

    @Autowired
    TeacherService teacherService;

    @Autowired
    Mapper mapper;

    @GetMapping("/teachers")
    public String getAllTeachers(
            @ModelAttribute String name,
            Model model
    ) {
        List<Teacher> teachers = teacherService.findAllTeachers();
        teachers.forEach(System.out::println);
        model.addAttribute("listOfTeachers", teachers);
        return "index";
    }

    @PostMapping("/teachers")
    public String newTeacher() {

        TeacherDto dto = new TeacherDto();
        dto.setName("Petr");
        dto.setLastName("Ivanov");
        dto.setSubjectName("English");

        Teacher teacher = mapper.convertDto(dto);
        teacherService.saveTeacher(teacher);
        return "index";
    }


}
