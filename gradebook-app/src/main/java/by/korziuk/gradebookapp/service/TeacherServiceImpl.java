package by.korziuk.gradebookapp.service;

import by.korziuk.gradebookapp.data.TeacherRepository;
import by.korziuk.gradebookapp.model.Teacher;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class TeacherServiceImpl implements TeacherService {

    @Autowired
    private TeacherRepository teacherRepository;

    @Override
    public Teacher readTeacher(String id) {
        return teacherRepository.getReferenceById(id);
    }

    @Override
    public Teacher saveTeacher(Teacher teacher) {
        return teacherRepository.save(teacher);
    }

    @Override
    public Teacher updateTeacher(Teacher teacher) {
        return null;
    }

    @Override
    public void deleteTeacher(String id) {
        teacherRepository.deleteById(id);
    }
}
