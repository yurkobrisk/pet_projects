package by.korziuk.gradebookapp.controller;

import by.korziuk.gradebookapp.dto.GroupExamsDTO;
import by.korziuk.gradebookapp.model.Exam;
import by.korziuk.gradebookapp.model.Group;
import by.korziuk.gradebookapp.model.Student;
import by.korziuk.gradebookapp.service.GroupService;
import by.korziuk.gradebookapp.service.mapper.MapService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.text.SimpleDateFormat;
import java.util.*;
import java.util.stream.Collectors;

@Controller
@RequestMapping("/gradebook/exams")
@RequiredArgsConstructor
@Slf4j
public class ExamController {

    private final GroupService groupService;
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
        log.info("PostMapping started. Group id: {}", dto.getId());
        Group group = groupService.get(dto.getId());

        mapService.toExams(dto).forEach(exam ->
            Objects.requireNonNull(group.getStudents()
                            .stream()
                            .filter(student -> student.getId().equals(exam.getStudent().getId()))
                            .findFirst()
                            .orElse(null))
                    .addExam(exam)
        );
        groupService.update(group);
        model.addAttribute("group", group);

        List<String> dates = group.getStudents().stream()
                .findFirst()
                .orElseThrow()
                .getExams()
                .stream()
                .map(Exam::getDate)
                .map(date -> new SimpleDateFormat("yyyy-MM-dd hh:mm").format(date))
                .sorted()
                .collect(Collectors.toList());
        model.addAttribute("dates", dates);

        Map<String, Map<String, String>> students = new HashMap<>(); //key: name + lastName; value: map< date, grade>
        for (Student student : group.getStudents()) {
            List<Exam> examList = student.getExams();
            SortedMap<String, String> map = new TreeMap<>();
            for (Exam exam : examList) {
                map.put(exam.getDate().toString(), exam.getGrade().toString());
            }
            students.put(student.getLastName() + " " + student.getName(), map);
        }
        model.addAttribute("students", students);

        return "view-group-exams";
    }
}
