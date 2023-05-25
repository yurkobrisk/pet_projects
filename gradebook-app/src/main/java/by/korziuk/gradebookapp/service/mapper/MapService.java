package by.korziuk.gradebookapp.service.mapper;

import by.korziuk.gradebookapp.data.TeacherRepository;
import by.korziuk.gradebookapp.model.Group;
import by.korziuk.gradebookapp.model.Teacher;
import by.korziuk.gradebookapp.dto.GroupTeacherDTO;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;

@RequiredArgsConstructor
@Service
@Transactional
@Slf4j
public class MapService {

    private final TeacherRepository teacherRepository;

    public GroupTeacherDTO toDto(Group group) {
        log.info("Converting to DTO. Getting group with ID: {}", group.getId());
        if (group.getTeacher() == null) {
            return new GroupTeacherDTO(group.getId(), group.getName(), null);
        } else {
            return new GroupTeacherDTO(group.getId(), group.getName(), group.getTeacher().getId());
        }
    }

    public Group toGroup(GroupTeacherDTO dto) {
        log.info("Converting to Group. Fetching teacher by ID: {}", dto.getTeacherId());
        Teacher teacher = teacherRepository.findById(dto.getTeacherId()).orElse(null);
        return new Group(dto.getId(), dto.getName(), new ArrayList<>(), teacher);
    }
}
