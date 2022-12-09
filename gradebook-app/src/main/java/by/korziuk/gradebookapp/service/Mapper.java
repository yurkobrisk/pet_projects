package by.korziuk.gradebookapp.service;

import by.korziuk.gradebookapp.model.Teacher;
import org.springframework.stereotype.Component;

@Component
public class Mapper {

    public Teacher convertDto(TeacherDto dto) {
        if (dto != null) {
            Teacher teacher = new Teacher();
            teacher.setName(dto.getName());
            teacher.setLastName(dto.getLastName());
            teacher.setSubjectName(dto.getSubjectName());
            return teacher;
        }
        return null;
    }

    public TeacherDto convertTeacher(Teacher teacher) {
        if (teacher != null) {
            TeacherDto dto = new TeacherDto();
            dto.setName(teacher.getName());
            dto.setLastName(teacher.getLastName());
            dto.setSubjectName(teacher.getSubjectName());
            return dto;
        }
        return null;
    }
}
