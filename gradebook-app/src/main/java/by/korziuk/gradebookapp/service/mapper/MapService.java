package by.korziuk.gradebookapp.service.mapper;

import by.korziuk.gradebookapp.data.GroupRepository;
import by.korziuk.gradebookapp.data.StudentRepository;
import by.korziuk.gradebookapp.data.TeacherRepository;
import by.korziuk.gradebookapp.dto.GroupExamsDTO;
import by.korziuk.gradebookapp.dto.StudentDTO;
import by.korziuk.gradebookapp.model.Exam;
import by.korziuk.gradebookapp.model.Group;
import by.korziuk.gradebookapp.model.Student;
import by.korziuk.gradebookapp.dto.GroupDTO;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.text.SimpleDateFormat;
import java.time.Instant;
import java.util.*;
import java.util.stream.Collectors;

@RequiredArgsConstructor
@Service
@Transactional
@Slf4j
public class MapService {

    private final TeacherRepository teacherRepository;
    private final GroupRepository groupRepository;
    private final StudentRepository studentRepository;

    public GroupDTO toDto(Group group) {
        log.info("Converting to DTO. Getting group with ID: {}", group.getId());
        if (group.getStudents() == null) {
            if (group.getTeacher() == null) {
                return new GroupDTO(group.getId(), group.getName(), null, null);
            } else {
                return new GroupDTO(group.getId(), group.getName(), group.getTeacher().getId(), null);
            }
        } else {
            if (group.getTeacher() == null) {
                return new GroupDTO(group.getId(), group.getName(), null,
                        group.getStudents().stream()
                                .map(Student::getId)
                                .collect(Collectors.toList()));
            } else {
                return new GroupDTO(group.getId(), group.getName(), group.getTeacher().getId(),
                        group.getStudents().stream()
                                .map(Student::getId)
                                .collect(Collectors.toList()));
            }
        }
    }
    public Group toExams(GroupDTO dto) {
        log.info("Converting to Group. Fetching teacher by ID: {}. Fetching list of students.", dto.getTeacherId());
        Group group = new Group();
        group.setId(dto.getId());
        group.setName(dto.getName());
        group.setTeacher(teacherRepository.findById(dto.getTeacherId()).orElse(null));
        for (String id : dto.getStudentsId()) {
            group.addStudent(studentRepository.findStudentById(id));
        }

        return group;
    }

    public StudentDTO toDto(Student student) {
        log.info("Converting to DTO. Getting student with ID: {}", student.getId());
        if (student.getGroup() == null) {
            return new StudentDTO(student.getId(), student.getName(), student.getLastName(), null);
        } else {
            return new StudentDTO(student.getId(), student.getName(), student.getLastName(), student.getGroup().getId());
        }
    }

    public Student toStudent(StudentDTO dto) {
        log.info("Converting to Student. Fetching group by ID: {}", dto.getGroupId());
        Group group = groupRepository.findById(dto.getGroupId()).orElse(null);
        return new Student(dto.getId(), dto.getName(), dto.getLastName(), new ArrayList<>(), group);
    }

    public GroupExamsDTO toGroupExamsDto(Group group) {
        log.info("Converting to exams DTO. Getting group with ID: {}", group.getId());
        if (group.getTeacher() == null) {
            if (group.getStudents() == null) {
                return new GroupExamsDTO(group.getId(), group.getName(),
                        "", "", "",
                        dateToString(Date.from(Instant.now())), null);
            } else {
                Map<String, String []> exams = new HashMap<>();
                group.getStudents().forEach(student -> exams.put(
                        student.getId(),
                        new String[]{student.getName(), student.getLastName(), ""}));
                return new GroupExamsDTO(group.getId(), group.getName(),
                        "", "", "",
                        dateToString(Date.from(Instant.now())), exams);
            }
        } else {
            if (group.getStudents() == null) {
                return new GroupExamsDTO(group.getId(), group.getName(),
                        group.getTeacher().getId(), group.getTeacher().getName(), group.getTeacher().getLastName(),
                        dateToString(Date.from(Instant.now())), null);
            } else {
                Map<String, String []> exams = new HashMap<>();
                group.getStudents().forEach(student -> exams.put(
                        student.getId(),
                        new String[]{student.getName(), student.getLastName(), ""}));
                return new GroupExamsDTO(group.getId(), group.getName(),
                        group.getTeacher().getId(), group.getTeacher().getName(), group.getTeacher().getLastName(),
                        dateToString(Date.from(Instant.now())), exams);
            }
        }
    }

    public List<Exam> toExams(GroupExamsDTO dto) {
        log.info("Converting to Exams.");
        List<Exam> exams = new ArrayList<>();
        List<Student> students = new ArrayList<>();
        for (Map.Entry<String, String[]> entry : dto.getExams().entrySet()) {
            Student student = new Student(
                    entry.getKey(),
                    entry.getValue()[0],
                    entry.getValue()[1],
                    null,
                    new Group(dto.getId(), null, null, null));
            students.add(student);
            int grade = Integer.parseInt(entry.getValue()[2]);
            Exam exam = new Exam("", Date.from(Instant.now()), grade, student);
            exams.add(exam);
        }
        return exams;
    }

    public String dateToString(Date date) {
        return new SimpleDateFormat("yyyy-MM-dd hh:mm").format(date);
    }
}
