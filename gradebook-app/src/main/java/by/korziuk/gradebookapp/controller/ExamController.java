package by.korziuk.gradebookapp.controller;

import by.korziuk.gradebookapp.dto.GroupExamsDTO;
import by.korziuk.gradebookapp.model.Group;
import by.korziuk.gradebookapp.service.GroupService;
import by.korziuk.gradebookapp.service.StudentService;
import by.korziuk.gradebookapp.service.mapper.MapService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.Objects;

@Controller
@RequestMapping("/gradebook/exams")
@RequiredArgsConstructor
public class ExamController {

    private final GroupService groupService;
    private final StudentService studentService;
    private final MapService mapService;

    @GetMapping("/add")
    public String addExam(Model model,
                          @RequestParam(value = "groupId") String groupId) {
        Group group = groupService.get(groupId);
        GroupExamsDTO dto = mapService.toGroupExamsDto(group);
        model.addAttribute("dto", dto);
        return "add-exam";
    }

    @PostMapping("/add")
    public String addExam(
            @ModelAttribute("dto") GroupExamsDTO dto,
            Model model
    ) {
        Group group = groupService.get(dto.getId());

        mapService.toExams(dto).forEach(exam ->
            Objects.requireNonNull(group.getStudents()
                            .stream()
                            .filter(student -> student.getId().equals(exam.getStudent().getId()))
                            .findFirst()
                            .orElse(null))
                    .addExam(exam)
        );
        model.addAttribute("group", group);
        groupService.update(group);
        return "view-group-exams";
    }
}
