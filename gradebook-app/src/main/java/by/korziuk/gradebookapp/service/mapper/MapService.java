package by.korziuk.gradebookapp.service.mapper;

import by.korziuk.gradebookapp.data.GroupRepository;
import by.korziuk.gradebookapp.data.TeacherRepository;
import by.korziuk.gradebookapp.dto.StudentDTO;
import by.korziuk.gradebookapp.model.Group;
import by.korziuk.gradebookapp.model.Student;
import by.korziuk.gradebookapp.model.Teacher;
import by.korziuk.gradebookapp.dto.GroupDTO;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;

@RequiredArgsConstructor
@Service
@Transactional
@Slf4j
public class MapService {

    private final TeacherRepository teacherRepository;
    private final GroupRepository groupRepository;

    public GroupDTO toDto(Group group) {
        log.info("Converting to DTO. Getting group with ID: {}", group.getId());
        if (group.getTeacher() == null) {
            return new GroupDTO(group.getId(), group.getName(), null);
        } else {
            return new GroupDTO(group.getId(), group.getName(), group.getTeacher().getId());
        }
    }

    public Group toGroup(GroupDTO dto) {
        log.info("Converting to Group. Fetching teacher by ID: {}", dto.getTeacherId());
        Teacher teacher = teacherRepository.findById(dto.getTeacherId()).orElse(null);
        return new Group(dto.getId(), dto.getName(), new ArrayList<>(), teacher);
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
}
